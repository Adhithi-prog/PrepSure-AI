package com.example.data.ai

import com.example.BuildConfig
import com.example.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object InstructionSimplifierEngine {

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(25, TimeUnit.SECONDS)
        .build()

    suspend fun simplifyAndStructure(
        rawDoctorInstructions: String,
        procedureHint: String = ""
    ): MedicalInstructionDoc = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (_: Exception) {
            ""
        }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val geminiResult = callGeminiApi(rawDoctorInstructions, apiKey)
                if (geminiResult != null) {
                    return@withContext geminiResult
                }
            } catch (e: Exception) {
                // Fallback to rule-based clinical engine gracefully
            }
        }

        // Rule-based deterministic clinical simplifier
        fallbackRuleBasedSimplification(rawDoctorInstructions, procedureHint)
    }

    private fun callGeminiApi(rawText: String, apiKey: String): MedicalInstructionDoc? {
        val prompt = """
You are PREPSURE AI, an assistive healthcare instruction simplifier for Swasth Bharat.
CRITICAL SAFETY RULES:
1. Do not invent or add clinical instructions not present in the source text.
2. If fasting is not mentioned, do not guess hours.
3. Preserve original medical meaning at a 6th-grade reading level.
4. Output strict JSON with these keys:
{
  "titleEn": "...",
  "titleTa": "...",
  "simplifiedEn": "...",
  "simplifiedTa": "...",
  "fastingHours": 8,
  "chaperoneRequired": true,
  "criticalPoints": [
    {"titleEn": "...", "titleTa": "...", "descEn": "...", "descTa": "...", "urgent": true}
  ],
  "glossary": [
    {"term": "...", "defEn": "...", "defTa": "...", "context": "..."}
  ],
  "checklist": [
    {"titleEn": "...", "titleTa": "...", "category": "Days Before / Night Before / Morning Of / What to Bring", "categoryTa": "...", "dueEn": "...", "dueTa": "..."}
  ]
}

Source text to simplify:
$rawText
""".trimIndent()

        val jsonBody = JSONObject().apply {
            put("contents", org.json.JSONArray().put(
                JSONObject().put("parts", org.json.JSONArray().put(
                    JSONObject().put("text", prompt)
                ))
            ))
            put("generationConfig", JSONObject().put("responseMimeType", "application/json"))
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"
        val request = Request.Builder()
            .url(url)
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        val response = httpClient.newCall(request).execute()
        if (!response.isSuccessful) return null

        val responseString = response.body?.string() ?: return null
        val rootObj = JSONObject(responseString)
        val candidate = rootObj.getJSONArray("candidates").getJSONObject(0)
        val text = candidate.getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text")

        val parsed = JSONObject(text)
        val docId = "doc_${System.currentTimeMillis()}"

        val criticalList = mutableListOf<CriticalPoint>()
        val critArray = parsed.optJSONArray("criticalPoints")
        if (critArray != null) {
            for (i in 0 until critArray.length()) {
                val c = critArray.getJSONObject(i)
                criticalList.add(
                    CriticalPoint(
                        titleEn = c.optString("titleEn", "Key Notice"),
                        titleTa = c.optString("titleTa", "முக்கிய அறிவிப்பு"),
                        descriptionEn = c.optString("descEn", ""),
                        descriptionTa = c.optString("descTa", ""),
                        isUrgent = c.optBoolean("urgent", true)
                    )
                )
            }
        }

        val glossaryList = mutableListOf<GlossaryTerm>()
        val glossArray = parsed.optJSONArray("glossary")
        if (glossArray != null) {
            for (i in 0 until glossArray.length()) {
                val g = glossArray.getJSONObject(i)
                glossaryList.add(
                    GlossaryTerm(
                        term = g.optString("term", ""),
                        definitionEn = g.optString("defEn", ""),
                        definitionTa = g.optString("defTa", ""),
                        clinicalContext = g.optString("context", "")
                    )
                )
            }
        }

        val checklistList = mutableListOf<ChecklistItem>()
        val checkArray = parsed.optJSONArray("checklist")
        if (checkArray != null) {
            for (i in 0 until checkArray.length()) {
                val cl = checkArray.getJSONObject(i)
                checklistList.add(
                    ChecklistItem(
                        id = "cl_${UUID.randomUUID().toString().take(6)}",
                        titleEn = cl.optString("titleEn", ""),
                        titleTa = cl.optString("titleTa", ""),
                        category = cl.optString("category", "Days Before"),
                        categoryTa = cl.optString("categoryTa", "நாட்களுக்கு முன்"),
                        dueTimingEn = cl.optString("dueEn", "Before Procedure"),
                        dueTimingTa = cl.optString("dueTa", "பரிசோதனைக்கு முன்"),
                        isCompleted = false
                    )
                )
            }
        }

        return MedicalInstructionDoc(
            id = docId,
            titleEn = parsed.optString("titleEn", "Medical Screening Preparation"),
            titleTa = parsed.optString("titleTa", "மருத்துவ பரிசோதனை தயாரிப்பு வழிகாட்டி"),
            category = ProcedureCategory.ENDOSCOPY,
            doctorOrClinicName = "Uploaded Clinical Instruction Sheet",
            originalText = rawText,
            simplifiedTextEn = parsed.optString("simplifiedEn", rawText),
            simplifiedTextTa = parsed.optString("simplifiedTa", "மருத்துவ வழிகாட்டுதல்கள்"),
            criticalPoints = criticalList,
            glossary = glossaryList,
            checklist = checklistList,
            fastingHours = parsed.optInt("fastingHours", 0),
            chaperoneRequired = parsed.optBoolean("chaperoneRequired", false)
        )
    }

    fun fallbackRuleBasedSimplification(
        rawText: String,
        procedureHint: String = ""
    ): MedicalInstructionDoc {
        val lower = rawText.lowercase()

        // Detect matching known procedure if closely aligned
        if (lower.contains("endoscop") || lower.contains("egd") || lower.contains("esophagogastroduodenoscopy")) {
            return com.example.data.sample.SampleMedicalInstructions.endoscopyInstruction.copy(
                id = "doc_${System.currentTimeMillis()}",
                originalText = rawText
            )
        } else if (lower.contains("colono") || lower.contains("peglec") || lower.contains("bowel")) {
            return com.example.data.sample.SampleMedicalInstructions.colonoscopyInstruction.copy(
                id = "doc_${System.currentTimeMillis()}",
                originalText = rawText
            )
        } else if (lower.contains("lipid") || lower.contains("fasting blood") || lower.contains("glucose") || lower.contains("fbs")) {
            return com.example.data.sample.SampleMedicalInstructions.bloodSugarInstruction.copy(
                id = "doc_${System.currentTimeMillis()}",
                originalText = rawText
            )
        } else if (lower.contains("ultrasound") || lower.contains("usg") || lower.contains("sonography") || lower.contains("bladder")) {
            return com.example.data.sample.SampleMedicalInstructions.ultrasoundInstruction.copy(
                id = "doc_${System.currentTimeMillis()}",
                originalText = rawText
            )
        } else if (lower.contains("mri") || lower.contains("magnetic") || lower.contains("contrast") || lower.contains("gadolinium")) {
            return com.example.data.sample.SampleMedicalInstructions.mriInstruction.copy(
                id = "doc_${System.currentTimeMillis()}",
                originalText = rawText
            )
        }

        // Generic clinical document parser
        val hasFasting = lower.contains("fasting") || lower.contains("npo") || lower.contains("empty stomach") || lower.contains("hours prior")
        val fastingHours = extractFastingHours(lower)

        val requiresEscort = lower.contains("escort") || lower.contains("chaperone") || lower.contains("sedation") || lower.contains("drive") || lower.contains("anesthesia")

        val criticalPoints = mutableListOf<CriticalPoint>()
        if (hasFasting) {
            criticalPoints.add(
                CriticalPoint(
                    titleEn = if (fastingHours > 0) "Strict $fastingHours-Hour Fasting" else "Fasting Required",
                    titleTa = if (fastingHours > 0) "$fastingHours மணி நேர உண்ணாநோன்பு" else "உண்ணாநோன்பு தேவை",
                    descriptionEn = "Do not eat solid meals prior to your procedure. Follow your clinic's cutoff strictly.",
                    descriptionTa = "பரிசோதனைக்கு முன் உணவு உண்ண வேண்டாம். மருத்துவமனை நேரத்தை சரியாகப் பின்பற்றவும்.",
                    isUrgent = true
                )
            )
        }

        if (requiresEscort) {
            criticalPoints.add(
                CriticalPoint(
                    titleEn = "Adult Companion Required",
                    titleTa = "துணைவர் உடன் வர வேண்டும்",
                    descriptionEn = "You will receive sedative medicines and cannot operate a vehicle or travel alone.",
                    descriptionTa = "மயக்க மருந்து தரப்படலாம் என்பதால் நீங்களே வாகனம் ஓட்டக்கூடாது; ஒருவர் உடன் வர வேண்டும்.",
                    isUrgent = true
                )
            )
        }

        if (lower.contains("aspirin") || lower.contains("blood thinner") || lower.contains("warfarin") || lower.contains("clopidogrel")) {
            criticalPoints.add(
                CriticalPoint(
                    titleEn = "Review Blood-Thinning Medications",
                    titleTa = "இரத்த நீர்ப்பு மருந்து மறுஆய்வு",
                    descriptionEn = "Consult your prescribing doctor before pausing or continuing blood thinners.",
                    descriptionTa = "மருத்துவர் ஆலோசனையின்றி இரத்த நீர்ப்பு மருந்துகளை நிறுத்தவோ மாற்றவோ வேண்டாம்.",
                    isUrgent = true
                )
            )
        }

        val glossary = mutableListOf<GlossaryTerm>()
        if (lower.contains("npo")) {
            glossary.add(
                GlossaryTerm(
                    term = "NPO (Nil Per Os)",
                    definitionEn = "Nothing by mouth (no food, liquids, or gum).",
                    definitionTa = "வாய் வழியாக உணவு அல்லது தண்ணீர் எதுவும் உட்கொள்ளக்கூடாது.",
                    clinicalContext = "Prevents stomach contents from accidentally entering the lungs."
                )
            )
        }
        if (lower.contains("sedation") || lower.contains("sedative")) {
            glossary.add(
                GlossaryTerm(
                    term = "Sedation",
                    definitionEn = "Medicine that relaxes you and prevents pain during medical tests.",
                    definitionTa = "பரிசோதனையின் போது அமைதியாகவும் வலி இல்லாமலும் இருக்க உதவும் மருந்து.",
                    clinicalContext = "You remain breathing on your own but feel sleepy."
                )
            )
        }
        if (lower.contains("contrast") || lower.contains("dye")) {
            glossary.add(
                GlossaryTerm(
                    term = "Contrast Agent",
                    definitionEn = "Special medical liquid given through an IV to make organs highlight brightly on imaging.",
                    definitionTa = "ஸ்கேனில் உறுப்புகள் தெளிவாகத் தெரிய நரம்பில் செலுத்தப்படும் சிறப்பு மருந்து.",
                    clinicalContext = "Eliminated harmlessly by the kidneys within 24 hours."
                )
            )
        }

        val checklist = mutableListOf<ChecklistItem>()
        checklist.add(
            ChecklistItem(
                id = "gen_1",
                titleEn = "Confirm appointment time and directions to clinic",
                titleTa = "பரிசோதனை நேரம் மற்றும் மருத்துவமனை வழியை உறுதி செய்யவும்",
                category = "Days Before",
                categoryTa = "நாட்களுக்கு முன்",
                dueTimingEn = "2 Days Prior",
                dueTimingTa = "2 நாட்களுக்கு முன்",
                isCompleted = false
            )
        )
        if (hasFasting) {
            checklist.add(
                ChecklistItem(
                    id = "gen_2",
                    titleEn = "Stop solid food according to the fasting timeframe specified",
                    titleTa = "குறிப்பிட்ட நேரத்திற்கு முன் உணவு உண்பதை நிறுத்தவும்",
                    category = "Night Before",
                    categoryTa = "முந்தைய இரவு",
                    dueTimingEn = "Fasting Window",
                    dueTimingTa = "விரத நேரம்",
                    isCompleted = false
                )
            )
        }
        if (requiresEscort) {
            checklist.add(
                ChecklistItem(
                    id = "gen_3",
                    titleEn = "Ensure an adult family member is available to accompany and drive you",
                    titleTa = "உடன் வர குடும்ப உறுப்பினர் ஒருவரை உறுதி செய்யவும்",
                    category = "Days Before",
                    categoryTa = "நாட்களுக்கு முன்",
                    dueTimingEn = "Prior to Visit",
                    dueTimingTa = "மருத்துவமனைக்கு முன்",
                    isCompleted = false
                )
            )
        }
        checklist.add(
            ChecklistItem(
                id = "gen_4",
                titleEn = "Carry official ID proof, insurance card, and prior prescription list",
                titleTa = "அடையாள அட்டை, காப்பீட்டு அட்டை மற்றும் பழைய மருந்து சீட்டை எடுத்து வரவும்",
                category = "What to Bring",
                categoryTa = "எடுத்து வர வேண்டியவை",
                dueTimingEn = "Departure",
                dueTimingTa = "கிளம்பும் முன்",
                isCompleted = false
            )
        )

        val cleanSimplifiedEn = buildString {
            append("SIMPLIFIED INSTRUCTION OVERVIEW:\n")
            append("• Purpose: This guide breaks down your uploaded healthcare instructions into clear steps.\n")
            if (hasFasting) {
                append("• Fasting: Do not eat solid meals before the test. Adhere strictly to the requested time window.\n")
            }
            if (requiresEscort) {
                append("• Escort: An adult companion is needed to help you return home safely.\n")
            }
            append("• Medicines: Check with your doctor before changing any current prescriptions.\n")
            append("• Safety Reminder: All guidance reflects the uploaded sheet. Contact your clinic with any doubts.")
        }

        val cleanSimplifiedTa = buildString {
            append("எளிய வழிகாட்டுதல் சுருக்கம்:\n")
            append("• நோக்கம்: உங்கள் மருத்துவ அறிவுரைகளை எளிதாகப் புரிந்துகொள்ளும் வகையில் இது தயாரிக்கப்பட்டுள்ளது.\n")
            if (hasFasting) {
                append("• விரதம்: பரிசோதனைக்கு முன் உணவு உண்ணக்கூடாது. குறிப்பிட்ட நேரக் கட்டுப்பாட்டை பின்பற்றவும்.\n")
            }
            if (requiresEscort) {
                append("• துணைவர்: உங்களை பாதுகாப்பாக வீட்டிற்கு அழைத்துச் செல்ல ஒருவர் உடன் வர வேண்டும்.\n")
            }
            append("• மருந்துகள்: உங்கள் வழக்கமான மருந்துகளை மாற்றுவதற்கு முன் மருத்துவரிடம் பேசவும்.\n")
            append("• பாதுகாப்பு: சந்தேகம் இருந்தால் உடனே உங்கள் மருத்துவரைத் தொடர்பு கொள்ளவும்.")
        }

        return MedicalInstructionDoc(
            id = "doc_${System.currentTimeMillis()}",
            titleEn = if (procedureHint.isNotBlank()) procedureHint else "Uploaded Medical Instruction",
            titleTa = if (procedureHint.isNotBlank()) "பதிவேற்றப்பட்ட மருத்துவ வழிகாட்டுதல்" else "மருத்துவ பரிசோதனை வழிகாட்டுதல்",
            category = ProcedureCategory.ENDOSCOPY,
            doctorOrClinicName = "Patient Instruction Sheet",
            originalText = rawText,
            simplifiedTextEn = cleanSimplifiedEn,
            simplifiedTextTa = cleanSimplifiedTa,
            criticalPoints = criticalPoints,
            glossary = glossary,
            checklist = checklist,
            fastingHours = fastingHours,
            chaperoneRequired = requiresEscort
        )
    }

    private fun extractFastingHours(text: String): Int {
        val pattern = Pattern.compile("(\\d{1,2})\\s*(?:-|to)?\\s*(\\d{1,2})?\\s*(?:hour|hr|hrs)")
        val matcher = pattern.matcher(text)
        if (matcher.find()) {
            val h1 = matcher.group(1)?.toIntOrNull() ?: 0
            val h2 = matcher.group(2)?.toIntOrNull()
            return h2 ?: h1
        }
        if (text.contains("overnight fast")) return 10
        if (text.contains("npo")) return 8
        return 0
    }
}
