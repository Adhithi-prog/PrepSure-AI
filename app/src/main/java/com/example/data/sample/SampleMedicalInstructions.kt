package com.example.data.sample

import com.example.data.model.*

object SampleMedicalInstructions {

    val endoscopyInstruction = MedicalInstructionDoc(
        id = "sample_endoscopy",
        titleEn = "Upper Gastrointestinal (GI) Endoscopy Preparation",
        titleTa = "மேல் இரைப்பை எண்டோஸ்கோபி பரிசோதனை வழிகாட்டுதல்",
        category = ProcedureCategory.ENDOSCOPY,
        doctorOrClinicName = "Department of Medical Gastroenterology, Apollo Hospitals / GH Chennai",
        originalText = """PATIENT PRE-PROCEDURE INSTRUCTION SHEET: ESOPHAGOGASTRODUODENOSCOPY (EGD)
1. FASTING (NPO GUIDELINES): Strict Nil Per Os (NPO) status is mandatory for a minimum of 8 hours prior to the scheduled examination. No solid foods, milk, juices with pulp, or chewing gum. Small sips of clear water are permissible up to 2 hours before sedation.
2. PHARMACOTHERAPY MANAGEMENT: Anticoagulants and antiplatelet aggregators (e.g., Clopidogrel, Ticagrelor, Warfarin) must be discontinued 5 days prior strictly upon cardiology concurrence. Oral hypoglycemic agents should be withheld on the morning of the procedure. Routine antihypertensives (e.g., Amlodipine, Telmisartan) may be ingested at 06:00 AM with 30 ml of water.
3. CONSCIOUS SEDATION & DISCHARGE POLICY: Conscious sedation with IV Midazolam and Fentanyl may be administered. The patient is legally prohibited from operating motor vehicles, operating machinery, or signing contractual documents for 24 hours post-sedation. A responsible adult escort (chaperone) must accompany the patient and remain in the endoscopy waiting lounge.
4. ATTIRE & BELONGINGS: Remove dental prostheses (dentures), spectacles, contact lenses, and metallic jewelry prior to entering the procedure suite. Wear loose-fitting, front-opening cotton garments.
5. REPORTING: Report to Endoscopy Reception counter 45 minutes prior to appointment with government identity card, prior endoscopy reports, and recent ECG.""",
        simplifiedTextEn = """SIMPLE EXPLANATION FOR YOUR ENDOSCOPY:
• What is this test?: The doctor uses a thin, flexible tube with a tiny light and camera to safely look inside your food pipe, stomach, and small intestine.
• Food & Drinks (Fasting): Do not eat any solid food or drink milk or juice for 8 hours before the test. You may take small sips of plain water up to 2 hours before the test. Keep your stomach completely empty so the doctor can see clearly and keep you safe.
• Your Daily Medicines: If you take blood thinners (like Aspirin or Clopidogrel), talk to your heart doctor 5 days before to pause them safely. If you take blood pressure medicine, you may swallow it with a small sip of water early in the morning. If you take diabetes tablets, skip them on the morning of the test because you are fasting.
• Going Home Safely: You will receive mild relaxing medicine so you do not feel discomfort. Because of this medicine, you will be sleepy and CANNOT drive yourself home. A family member or friend must come with you to help you get home safely.
• What to Wear: Wear loose, comfortable clothes. Leave gold and jewelry at home. Take off dentures and spectacles before entering the exam room.""",
        simplifiedTextTa = """உங்கள் எண்டோஸ்கோபி பரிசோதனைக்கான எளிய விளக்கம்:
• பரிசோதனை என்ன?: சிறிய கேமரா பொருத்தப்பட்ட மெல்லிய குழாய் மூலம் உங்கள் உணவுக்குழாய் மற்றும் இரைப்பையை மருத்துவர் பாதுகாப்பாக பரிசோதிப்பார்.
• உணவு மற்றும் குடிநீர் (உண்ணாநோன்பு): பரிசோதனைக்கு 8 மணி நேரத்திற்கு முன் எந்த திட உணவும், பாலும், பழச்சாறும் சாப்பிடக்கூடாது. பரிசோதனைக்கு 2 மணி நேரம் முன்பு வரை சிறிதளவு குடிநீர் மட்டும் அருந்தலாம். வயிறு காலியாக இருப்பது உங்கள் பாதுகாப்பிற்கு மிக அவசியம்.
• தினசரி மருந்துகள்: இரத்தம் நீர்க்கும் மருந்துகள் (Aspirin போன்றவை) சாப்பிடுபவர்கள் 5 நாட்களுக்கு முன்பே இதய மருத்துவரிடம் ஆலோசித்து நிறுத்த வேண்டும். இரத்த அழுத்த (BP) மாத்திரையை அதிகாலையில் சிறிதளவு தண்ணீருடன் போடலாம். சர்க்கரை நோய் மாத்திரைகளை அன்று காலை போடக்கூடாது.
• துணைக்கு ஒருவர் தேவை: பரிசோதனையின் போது மயக்க மருந்து அல்லது தூக்க மருந்து தரப்படுவதால், நீங்கள் தானாக வண்டி ஓட்டிச் செல்ல முடியாது. உங்களை வீட்டிற்கு அழைத்துச் செல்ல கட்டாயம் ஒரு துணைவர் வர வேண்டும்.
• உடை: தளர்வான பருத்தி ஆடை அணியவும். நகைகளை வீட்டிலேயே வைத்துவிடவும்.""",
        criticalPoints = listOf(
            CriticalPoint(
                titleEn = "Strict 8-Hour Fasting",
                titleTa = "8 மணி நேர முழு பட்டினி",
                descriptionEn = "No food, tea, or milk for 8 hours before the test. Sips of plain water allowed up to 2 hours prior.",
                descriptionTa = "பரிசோதனைக்கு 8 மணி நேரத்திற்கு முன் உணவு, டீ, பால் அருந்தக்கூடாது. 2 மணி நேரம் முன்பு வரை சிறிதளவு தண்ணீர் மட்டும் குடிக்கலாம்.",
                isUrgent = true
            ),
            CriticalPoint(
                titleEn = "Adult Escort Required",
                titleTa = "துணைவர் கட்டாயம் தேவை",
                descriptionEn = "You will receive relaxing sedation and cannot drive home. An adult caregiver must accompany you.",
                descriptionTa = "மயக்க மருந்து தரப்படுவதால் நீங்களே வாகனம் ஓட்டக்கூடாது. உங்களை அழைத்துச்செல்ல ஒருவர் உடன் வர வேண்டும்.",
                isUrgent = true
            ),
            CriticalPoint(
                titleEn = "Stop Blood Thinners 5 Days Prior",
                titleTa = "இரத்த நீர்ப்பு மருந்து நிறுத்தம்",
                descriptionEn = "Consult cardiologist before pausing medicines like Clopidogrel or Warfarin.",
                descriptionTa = "மருத்துவர் ஆலோசனையுடன் இரத்த நீர்ப்பு மருந்துகளை 5 நாட்களுக்கு முன் நிறுத்த வேண்டும்.",
                isUrgent = true
            )
        ),
        glossary = listOf(
            GlossaryTerm(
                term = "NPO (Nil Per Os)",
                definitionEn = "A medical term meaning 'nothing by mouth' — no food, drink, or gum.",
                definitionTa = "வாய் வழியாக உணவு, தண்ணீர் அல்லது சுவிங்கம் எதுவும் உட்கொள்ளக்கூடாது என்று பொருள்.",
                clinicalContext = "Keeping the stomach empty prevents vomiting or stomach contents from entering the lungs while sedated."
            ),
            GlossaryTerm(
                term = "Conscious Sedation",
                definitionEn = "Medication that makes you relaxed and drowsy so you do not feel pain or stress.",
                definitionTa = "பரிசோதனையின் போது வலி அல்லது பயம் தெரியாமல் இருக்க கொடுக்கப்படும் மிதமான மயக்க மருந்து.",
                clinicalContext = "You remain breathing on your own but feel calm and sleep through most of the procedure."
            ),
            GlossaryTerm(
                term = "Anticoagulants / Blood Thinners",
                definitionEn = "Medicines that slow down blood clotting (e.g., Aspirin, Clopidogrel, Warfarin).",
                definitionTa = "இரத்தம் உறைவதைத் தடுக்கும் மருந்துகள்.",
                clinicalContext = "Paused temporarily so that taking small stomach biopsies will not cause bleeding."
            ),
            GlossaryTerm(
                term = "Chaperone / Adult Escort",
                definitionEn = "A responsible family member or friend who accompanies you to the hospital.",
                definitionTa = "உங்களுடன் மருத்துவமனைக்கு வந்து உங்களை பாதுகாப்பாக வீட்டிற்கு அழைத்துச் செல்லும் குடும்ப உறுப்பினர்.",
                clinicalContext = "Required by hospital safety policies whenever sedation is given."
            )
        ),
        checklist = listOf(
            ChecklistItem(
                id = "endo_1",
                titleEn = "Contact cardiologist about pausing blood-thinning medicine",
                titleTa = "இரத்த நீர்ப்பு மருந்தை நிறுத்த இதய மருத்துவரிடம் ஆலோசனை பெறவும்",
                category = "Days Before",
                categoryTa = "நாட்களுக்கு முன்",
                dueTimingEn = "5 Days Before Appointment",
                dueTimingTa = "பரிசோதனைக்கு 5 நாட்களுக்கு முன்",
                isCompleted = true
            ),
            ChecklistItem(
                id = "endo_2",
                titleEn = "Arrange for an adult family member or friend to drive you home",
                titleTa = "உங்களை வீட்டிற்கு அழைத்துச் செல்ல ஒரு குடும்ப நபரை உறுதி செய்யவும்",
                category = "Days Before",
                categoryTa = "நாட்களுக்கு முன்",
                dueTimingEn = "2 Days Before Appointment",
                dueTimingTa = "பரிசோதனைக்கு 2 நாட்களுக்கு முன்",
                isCompleted = true
            ),
            ChecklistItem(
                id = "endo_3",
                titleEn = "Stop eating solid foods, milk, and tea at 10:00 PM (Start 8-hr fast)",
                titleTa = "இரவு 10:00 மணிக்கு உணவு, பால், டீ உண்பதை நிறுத்தவும் (8 மணி நேர உண்ணாநோன்பு)",
                category = "Night Before",
                categoryTa = "முந்தைய இரவு",
                dueTimingEn = "Night Before (10:00 PM)",
                dueTimingTa = "முந்தைய இரவு (10:00 PM)",
                isCompleted = false
            ),
            ChecklistItem(
                id = "endo_4",
                titleEn = "Take morning blood pressure tablet with a small sip of water at 6:00 AM",
                titleTa = "அதிகாலை 6:00 மணிக்கு ரத்த அழுத்த மாத்திரையை சிறிதளவு தண்ணீருடன் குடிக்கவும்",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "Morning of Exam (6:00 AM)",
                dueTimingTa = "பரிசோதனை காலை (6:00 AM)",
                isCompleted = false
            ),
            ChecklistItem(
                id = "endo_5",
                titleEn = "Do NOT take morning diabetes medication (protects against low blood sugar)",
                titleTa = "சர்க்கரை நோய் காலை மாத்திரையை போட வேண்டாம் (சர்க்கரை குறையாமல் தடுக்க)",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "Morning of Exam",
                dueTimingTa = "பரிசோதனை காலை",
                isCompleted = false
            ),
            ChecklistItem(
                id = "endo_6",
                titleEn = "Bring Government ID proof, previous ECG, and list of daily medicines",
                titleTa = "அடையாள அட்டை, முந்தைய ECG அறிக்கை மற்றும் மருந்து சீட்டை எடுத்து வரவும்",
                category = "What to Bring",
                categoryTa = "எடுத்து வர வேண்டியவை",
                dueTimingEn = "Before Leaving Home",
                dueTimingTa = "வீட்டில் இருந்து கிளம்பும் முன்",
                isCompleted = false
            ),
            ChecklistItem(
                id = "endo_7",
                titleEn = "Wear comfortable loose clothes; remove dentures and gold jewelry",
                titleTa = "தளர்வான ஆடைகள் அணியவும்; பொய் பல் மற்றும் நகைகளை கழற்றி வைக்கவும்",
                category = "What to Bring",
                categoryTa = "எடுத்து வர வேண்டியவை",
                dueTimingEn = "Before Leaving Home",
                dueTimingTa = "வீட்டில் இருந்து கிளம்பும் முன்",
                isCompleted = false
            )
        ),
        fastingHours = 8,
        chaperoneRequired = true
    )

    val colonoscopyInstruction = MedicalInstructionDoc(
        id = "sample_colonoscopy",
        titleEn = "Colonoscopy Bowel Cleansing & Diet Protocol",
        titleTa = "பெருங்குடல் பரிசோதனை குடல் சுத்திகரிப்பு வழிகாட்டுதல்",
        category = ProcedureCategory.COLONOSCOPY,
        doctorOrClinicName = "Digestive Diseases Centre, Stanley Medical College & Hospital",
        originalText = """COLONOSCOPY PREPARATION PROTOCOL (SPLIT-DOSE PEGLEC / PEG)
1. DIETARY MODIFICATIONS (3 DAYS PRIOR): Switch to a low-fiber residue diet. Discontinue whole seeds, nuts, corn, multi-grain rotis, raw vegetables, and iron supplements.
2. CLEAR LIQUID REGIMEN (24 HOURS PRIOR): Consume ONLY clear liquids for the entire calendar day prior to the procedure. Permitted: clear strained chicken or vegetable broth, coconut water, apple juice, plain water, black tea/coffee without dairy. Strictly forbidden: red, purple, or blue-dyed liquids, dairy products, alcoholic beverages, and solid food.
3. BOWEL CLEANSING REGIMEN: Reconstitute one sachet of Polyethylene Glycol (PEG) electrolyte powder into 2 liters of drinking water. Dose 1: Ingest 1 liter between 18:00 and 19:30 on the preceding evening (one 250ml glass every 15 minutes). Dose 2: Ingest the remaining 1 liter 4 to 5 hours prior to the scheduled appointment.
4. EXPECTED RESPONSE: Expect frequent watery stools starting within 60 minutes. The endpoint is pale, clear yellow watery effluent devoid of solid particulate matter. If stool remains turbid or dark, immediately alert the endoscopy unit.
5. FASTING: Cease all fluid intake 2 hours prior to arrival time.""",
        simplifiedTextEn = """SIMPLE EXPLANATION FOR COLONOSCOPY PREPARATION:
• Why this preparation is critical: The camera inside your large intestine can only see small polyps or spots if the bowel walls are completely clean.
• 3 Days Before: Eat soft, low-fiber foods (white rice, idli, bread). Do NOT eat seeds, corn, raw vegetables, or brown rice.
• 1 Day Before (Clear Liquids Only): No solid food all day! Drink clear fluids only: tender coconut water, strained rasam or clear soup, apple juice, and plain water. Do not drink anything red or purple, and no milk or tea with milk.
• Medicine Drink (PEG Solution): Mix the packet with 2 liters of clean water. Drink the first half the evening before. Drink the second half early in the morning 4 hours before your hospital visit.
• How to know it worked: Your bowel motions will become clear, pale yellow liquid like urine with no solid bits.
• Stop drinking all fluids 2 hours before the hospital appointment.""",
        simplifiedTextTa = """பெருங்குடல் பரிசோதனைக்கான எளிய வழிகாட்டுதல்:
• ஏன் இந்த தயாரிப்பு முக்கியம்?: பெருங்குடல் சுத்தமாக இருந்தால் மட்டுமே மருத்துவரால் எந்தவொரு சிறிய தழும்புகளையும் துல்லியமாக பார்க்க முடியும்.
• 3 நாட்களுக்கு முன்: நார்ச்சத்து குறைவான எளிய உணவுகள் (இட்லி, சாதம்) சாப்பிடவும். காய்கறி விதைகள், சோளம், முழுக் தானியங்கள் சாப்பிடக்கூடாது.
• 1 நாளுக்கு முன் (தெளிந்த திரவ உணவு மட்டும்): திட உணவு உண்ணக்கூடாது! இளநீர், வடித்த ரசம், சூப், ஆப்பிள் ஜூஸ், தண்ணீர் மட்டும் குடிக்கலாம். பால், டீ, காபி மற்றும் சிவப்பு நிற பானங்கள் குடிக்கக்கூடாது.
• குடல் தூய்மைப்படுத்தும் மருந்து: பொடியை 2 லிட்டர் நீரில் கலந்து, முதல் பாதியை மாலையிலும், மீதியை காலை பரிசோதனைக்கு 4 மணி நேரம் முன்பும் குடிக்கவும்.
• வயிறு சுத்தமானதை அறிவது எப்படி?: மலம் சிறுநீர் போல தெளிந்த மஞ்சள் நிற திரவமாக வெளியேறும் போது வயிறு சுத்தமாகிவிட்டது என்று பொருள்.
• மருத்துவமனை செல்லும் 2 மணி நேரத்திற்கு முன் தண்ணீர் குடிப்பதையும் நிறுத்த வேண்டும்.""",
        criticalPoints = listOf(
            CriticalPoint(
                titleEn = "24-Hour Clear Liquids Only",
                titleTa = "24 மணி நேர திரவ உணவு மட்டும்",
                descriptionEn = "Strictly no solid food on the day before the test. Drink coconut water, clear soup, or apple juice.",
                descriptionTa = "பரிசோதனைக்கு முந்தைய நாள் முழுவதும் திட உணவு சாப்பிடக்கூடாது. இளநீர், வடித்த சூப் மட்டுமே அருந்த வேண்டும்.",
                isUrgent = true
            ),
            CriticalPoint(
                titleEn = "Split-Dose Bowel Drink",
                titleTa = "குடல் சுத்தம் செய்யும் மருந்து",
                descriptionEn = "Drink half the mixture the night before, and finish the second half early in the morning.",
                descriptionTa = "மருந்தை மாலையிலும் அதிகாலை 4 மணி நேரம் முன்பும் இரு தவணைகளாக குடிக்க வேண்டும்.",
                isUrgent = true
            )
        ),
        glossary = listOf(
            GlossaryTerm(
                term = "Polyethylene Glycol (PEG / Peglec)",
                definitionEn = "A gentle laxative powder dissolved in water that flushes out the entire bowel cleanly.",
                definitionTa = "குடலில் உள்ள மலத்தை முழுமையாக வெளியேற்றி சுத்தம் செய்யும் மருத்துவக் கரைசல்.",
                clinicalContext = "Does not dehydrate the body when mixed with the correct amount of water."
            ),
            GlossaryTerm(
                term = "Low-Residue Diet",
                definitionEn = "Foods that leave very little waste in your digestive tract (e.g., white rice, boiled potatoes).",
                definitionTa = "செரிமானத்திற்கு பின் குறைந்த கழிவை உருவாக்கும் எளிய உணவு (எ.கா: இட்லி, வடித்த சாதம்).",
                clinicalContext = "Helps the bowel cleansing drink work faster and more thoroughly."
            )
        ),
        checklist = listOf(
            ChecklistItem(
                id = "col_1",
                titleEn = "Purchase PEG prep solution from pharmacy and stop iron tablets",
                titleTa = "மருந்தகத்தில் குடல் தூய்மை மருந்து வாங்கவும்; இரும்புச்சத்து மாத்திரைகளை நிறுத்தவும்",
                category = "Days Before",
                categoryTa = "நாட்களுக்கு முன்",
                dueTimingEn = "3 Days Before",
                dueTimingTa = "3 நாட்களுக்கு முன்",
                isCompleted = true
            ),
            ChecklistItem(
                id = "col_2",
                titleEn = "Start Clear Liquid Diet all day (no solid food, no milk)",
                titleTa = "முழு நாளும் தெளிந்த திரவ உணவு மட்டும் (திட உணவு, பால் கூடாது)",
                category = "Days Before",
                categoryTa = "நாட்களுக்கு முன்",
                dueTimingEn = "1 Day Before (All Day)",
                dueTimingTa = "1 நாளுக்கு முன் (நாள் முழுவதும்)",
                isCompleted = false
            ),
            ChecklistItem(
                id = "col_3",
                titleEn = "Drink 1st liter of PEG solution between 6:00 PM and 7:30 PM",
                titleTa = "மாலை 6:00 மணி முதல் 7:30 மணிக்குள் முதல் 1 லிட்டர் மருந்தை குடிக்கவும்",
                category = "Night Before",
                categoryTa = "முந்தைய இரவு",
                dueTimingEn = "Night Before (6:00 PM)",
                dueTimingTa = "முந்தைய இரவு (6:00 PM)",
                isCompleted = false
            ),
            ChecklistItem(
                id = "col_4",
                titleEn = "Drink 2nd liter of PEG solution 4 hours before hospital arrival",
                titleTa = "மருத்துவமனை செல்லும் 4 மணி நேரம் முன்பு 2வது 1 லிட்டர் மருந்தை குடிக்கவும்",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "Morning of Exam",
                dueTimingTa = "பரிசோதனை காலை",
                isCompleted = false
            )
        ),
        fastingHours = 12,
        chaperoneRequired = true
    )

    val bloodSugarInstruction = MedicalInstructionDoc(
        id = "sample_bloodsugar",
        titleEn = "Fasting Blood Glucose & Lipid Profile Protocol",
        titleTa = "வெறும் வயிற்று சர்க்கரை & கொழுப்பு பரிசோதனை வழிகாட்டுதல்",
        category = ProcedureCategory.BLOOD_TEST,
        doctorOrClinicName = "Preventive Health Diagnostics, Primary Health Centre / Rajiv Gandhi Hospital",
        originalText = """PATIENT PRE-ANALYTICAL FASTING INSTRUCTION:
1. FASTING DURATION: Fasting duration must be strictly 10 to 12 hours prior to venipuncture. Fasting for less than 10 hours or exceeding 14 hours produces skewed triglycerides and glycemic indexes.
2. FLUID PERMISSIBILITY: Plain unflavored water is encouraged in moderation to maintain vascular hydration and ease phlebotomy. Strict abstinence from coffee, tea, milk, sweetened drinks, and smoking.
3. MEDICATION GUIDELINES: Do not administer morning insulin or oral hypoglycemic agents prior to the fasting blood draw to avert hypoglycemia. Bring your medication and morning meal to the lab to take immediately after blood collection.
4. PHYSICAL ACTIVITY: Refrain from strenuous exercise, brisk walking, or heavy physical exertion during the fasting window.""",
        simplifiedTextEn = """SIMPLE EXPLANATION FOR FASTING BLOOD & CHOLESTEROL TESTS:
• Fasting Time: Keep an overnight fast of 10 to 12 hours. For example, if your blood will be taken at 8:00 AM, finish dinner by 9:00 PM the night before.
• Drinking Water: You CAN and SHOULD drink plain water. Drinking water keeps your veins healthy so the blood draw is easy and painless. Do NOT drink tea, coffee, milk, or juice.
• Diabetes Pills & Insulin: Do NOT take your morning diabetes medicines before the blood test. If you take diabetes medicine without food, your blood sugar may drop dangerously low. Bring your medicines and breakfast box with you to take right after the test.
• Activity: Rest normally. Avoid strenuous gym workouts or fast running in the morning.""",
        simplifiedTextTa = """சர்க்கரை மற்றும் கொழுப்பு பரிசோதனைக்கான எளிய விளக்கம்:
• உண்ணாநோன்பு நேரம்: இரவு 10 முதல் 12 மணி நேரம் பட்டினி இருக்க வேண்டும். காலை 8 மணிக்கு ரத்தம் கொடுப்பதாக இருந்தால், இரவு 9 மணிக்குள் உணவை முடிக்கவும்.
• தண்ணீர் குடிக்கலாம்: சாதாரண குடிநீர் தாராளமாக குடிக்கலாம். இது நரம்புகளை எளிதாகக் கண்டறிய உதவும். ஆனால் டீ, காபி, பால் குடிக்கக்கூடாது.
• சர்க்கரை மருந்துகள்: இரத்த பரிசோதனைக்கு முன் காலை சர்க்கரை மாத்திரை அல்லது இன்சுலின் போடக்கூடாது. சாப்பிடும் முன் போட்டால் சர்க்கரை குறைந்து மயக்கம் வரலாம். பரிசோதனை முடிந்தவுடன் சாப்பிட மாத்திரையையும் டிபனையும் எடுத்து வரவும்.""",
        criticalPoints = listOf(
            CriticalPoint(
                titleEn = "10 to 12 Hour Fast Only",
                titleTa = "10 முதல் 12 மணி நேர விரதம்",
                descriptionEn = "Fasting less than 10 hrs or more than 14 hrs produces inaccurate test results.",
                descriptionTa = "10 மணி நேரத்திற்கு குறைவாகவோ அல்லது 14 மணி நேரத்திற்கு மேலாகவோ விரதம் இருந்தால் முடிவு தவறாக வரலாம்.",
                isUrgent = true
            ),
            CriticalPoint(
                titleEn = "Plain Water is Allowed",
                titleTa = "குடிநீர் குடிக்கலாம்",
                descriptionEn = "Plain drinking water is safe and makes blood collection smoother.",
                descriptionTa = "சாதாரண தண்ணீர் குடிக்கலாம், இது ரத்த மாதிரியை எளிதில் எடுக்க உதவும்.",
                isUrgent = false
            ),
            CriticalPoint(
                titleEn = "Hold Morning Diabetes Medication",
                titleTa = "காலை சர்க்கரை மருந்தை நிறுத்தவும்",
                descriptionEn = "Bring morning medicine and breakfast box to consume immediately after the blood draw.",
                descriptionTa = "ரத்தம் எடுத்த பிறகு சாப்பிட மருந்து மற்றும் காலை உணவை உடன் எடுத்து வரவும்.",
                isUrgent = true
            )
        ),
        glossary = listOf(
            GlossaryTerm(
                term = "Fasting Blood Sugar (FBS)",
                definitionEn = "Blood sugar level measured after 10-12 hours without eating any food.",
                definitionTa = "10-12 மணி நேரம் உணவின்றி இருக்கும்போது அளவிடப்படும் இரத்த சர்க்கரை அளவு.",
                clinicalContext = "Establishes baseline sugar control without interference from recently digested meals."
            ),
            GlossaryTerm(
                term = "Lipid Profile / Triglycerides",
                definitionEn = "A set of blood tests measuring good cholesterol (HDL), bad cholesterol (LDL), and fats.",
                definitionTa = "இரத்தத்தில் உள்ள நல்ல மற்றும் கெட்ட கொழுப்புகளை அளவிடும் பரிசோதனை.",
                clinicalContext = "Eating food before the test creates temporary fats in the blood that falsify heart disease risk estimates."
            )
        ),
        checklist = listOf(
            ChecklistItem(
                id = "bs_1",
                titleEn = "Finish light dinner by 9:00 PM (begin 10-hour fasting)",
                titleTa = "இரவு 9:00 மணிக்குள் எளிய உணவை முடிக்கவும் (10 மணி நேர விரதம் தொடக்கம்)",
                category = "Night Before",
                categoryTa = "முந்தைய இரவு",
                dueTimingEn = "Night Before (9:00 PM)",
                dueTimingTa = "முந்தைய இரவு (9:00 PM)",
                isCompleted = false
            ),
            ChecklistItem(
                id = "bs_2",
                titleEn = "Drink a glass of plain water in the morning upon waking",
                titleTa = "காலையில் எழுந்ததும் ஒரு டம்ளர் சாதாரண தண்ணீர் குடிக்கவும்",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "Morning of Test (6:30 AM)",
                dueTimingTa = "பரிசோதனை காலை (6:30 AM)",
                isCompleted = false
            ),
            ChecklistItem(
                id = "bs_3",
                titleEn = "Pack morning diabetes tablets and breakfast box to take after blood collection",
                titleTa = "ரத்தம் கொடுத்த பிறகு உட்கொள்ள மருந்து மற்றும் காலை உணவை எடுத்துச் செல்லவும்",
                category = "What to Bring",
                categoryTa = "எடுத்து வர வேண்டியவை",
                dueTimingEn = "Before Leaving Home",
                dueTimingTa = "வீட்டில் இருந்து கிளம்பும் முன்",
                isCompleted = false
            )
        ),
        fastingHours = 10,
        chaperoneRequired = false
    )

    val ultrasoundInstruction = MedicalInstructionDoc(
        id = "sample_ultrasound",
        titleEn = "Abdominal & Pelvic Ultrasound (USG) Instructions",
        titleTa = "வயிற்று பகுதி அல்ட்ராசவுண்ட் ஸ்கேன் வழிகாட்டுதல்",
        category = ProcedureCategory.ULTRASOUND,
        doctorOrClinicName = "Department of Radiodiagnosis, Madras Medical College & Hospital",
        originalText = """ULTRASONOGRAPHY (USG) WHOLE ABDOMEN AND PELVIS PREPARATION:
1. GALLBLADDER FASTING: The patient must fast (no food, no milk) for 6 hours prior to the scan. This allows the gallbladder to remain distended with bile and minimizes bowel gas overlying the pancreas and liver.
2. FULL URINARY BLADDER (PELVIC SCAN): Ingest 4 to 5 glasses (approximately 1 liter) of plain water 1 hour prior to the examination time. Do NOT void (do not urinate) until the scan is concluded. An acoustic window created by an appropriately distended bladder is mandatory to image the prostate, uterus, ovaries, and urinary bladder walls.
3. ATTIRE: Wear two-piece comfortable clothing (shirt and trousers/skirt) so the abdomen can be easily exposed for gel application.""",
        simplifiedTextEn = """SIMPLE EXPLANATION FOR YOUR ABDOMINAL ULTRASOUND:
• Fasting (6 Hours): Do not eat solid food or drink milk for 6 hours before the scan. This allows the doctor to see your gallbladder and liver clearly without stomach gas blocking the view.
• Full Bladder Requirement: Drink 4 to 5 glasses of plain water 1 hour before the scan and HOLD YOUR URINE. A full bladder acts like a clear window that helps sound waves capture sharp pictures of your pelvic organs. You can use the restroom immediately after the doctor finishes!
• Clothing: Wear a two-piece dress (like a shirt and pants) so the doctor can easily apply the scanning gel to your tummy.""",
        simplifiedTextTa = """வயிற்று பகுதி அல்ட்ராசவுண்ட் ஸ்கேனுக்கான எளிய விளக்கம்:
• 6 மணி நேர விரதம்: ஸ்கேன் செய்வதற்கு 6 மணி நேரம் முன்பு உணவு அல்லது பால் சாப்பிடக்கூடாது. இது பித்தப்பை மற்றும் கல்லீரலை தெளிவாகப் பார்க்க உதவும்.
• சிறுநீர்ப்பை நிரம்பியிருக்க வேண்டும்: ஸ்கேன் செய்வதற்கு 1 மணி நேரம் முன்பு 4 அல்லது 5 டம்ளர் தண்ணீர் குடித்து, சிறுநீரை அடக்கி வைத்திருக்க வேண்டும். சிறுநீர்ப்பை நிறைந்திருந்தால் மட்டுமே தெளிவான படங்கள் கிடைக்கும்.
• உடை: வயிற்றுப் பகுதியில் ஜெல் தடவ ஏதுவாக இரண்டு துண்டு ஆடைகள் (சட்டை மற்றும் பேன்ட்) அணியவும்.""",
        criticalPoints = listOf(
            CriticalPoint(
                titleEn = "Full Bladder (Hold Urine)",
                titleTa = "சிறுநீரை அடக்கி வைத்திருக்கவும்",
                descriptionEn = "Drink 1 liter of water 1 hr before the scan and do NOT urinate until after the scan.",
                descriptionTa = "1 மணி நேரம் முன்பு 4 டம்ளர் தண்ணீர் குடித்து சிறுநீரை அடக்கி வைத்திருக்க வேண்டும்.",
                isUrgent = true
            ),
            CriticalPoint(
                titleEn = "6-Hour Fasting for Gallbladder",
                titleTa = "6 மணி நேர விரதம்",
                descriptionEn = "No meals or milk for 6 hours so the gallbladder is filled with bile and visible.",
                descriptionTa = "பித்தப்பை தெளிவாக தெரிய 6 மணி நேரம் உணவு உண்ணக்கூடாது.",
                isUrgent = true
            )
        ),
        glossary = listOf(
            GlossaryTerm(
                term = "Acoustic Window",
                definitionEn = "A fluid-filled area (like a full bladder) that lets ultrasound sound waves pass through easily.",
                definitionTa = "அல்ட்ராசவுண்ட் ஒலி அலைகள் எளிதாக ஊடுருவி செல்ல உதவும் நீர் நிறைந்த பகுதி.",
                clinicalContext = "Ultrasound waves cannot travel through air or gas, so water makes the organs visible."
            )
        ),
        checklist = listOf(
            ChecklistItem(
                id = "usg_1",
                titleEn = "Stop eating solid foods 6 hours before your appointment time",
                titleTa = "பரிசோதனைக்கு 6 மணி நேரம் முன்பு உணவு உண்பதை நிறுத்தவும்",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "6 Hours Before Scan",
                dueTimingTa = "ஸ்கேனுக்கு 6 மணி நேரம் முன்",
                isCompleted = false
            ),
            ChecklistItem(
                id = "usg_2",
                titleEn = "Drink 4 to 5 glasses of plain water 1 hour before the scan",
                titleTa = "ஸ்கேனுக்கு 1 மணி நேரம் முன்பு 4 முதல் 5 டம்ளர் தண்ணீர் குடிக்கவும்",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "1 Hour Before Scan",
                dueTimingTa = "ஸ்கேனுக்கு 1 மணி நேரம் முன்",
                isCompleted = false
            ),
            ChecklistItem(
                id = "usg_3",
                titleEn = "Do NOT urinate until the technician finishes your ultrasound",
                titleTa = "ஸ்கேன் முடியும் வரை சிறுநீர் கழிக்காமல் அடக்கி வைக்கவும்",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "Waiting Room",
                dueTimingTa = "காத்திருப்பு அறை",
                isCompleted = false
            )
        ),
        fastingHours = 6,
        chaperoneRequired = false
    )

    val mriInstruction = MedicalInstructionDoc(
        id = "sample_mri",
        titleEn = "Contrast-Enhanced MRI Scan Safety Instructions",
        titleTa = "எம்.ஆர்.ஐ ஸ்கேன் (கான்ட்ராஸ்ட்) பாதுகாப்பு வழிகாட்டுதல்",
        category = ProcedureCategory.MRI_SCAN,
        doctorOrClinicName = "Advanced Imaging Institute, Stanley Medical College / Apollo Diagnostics",
        originalText = """MRI (MAGNETIC RESONANCE IMAGING) SCREENING & CONTRAST SAFETY:
1. FERROMAGNETIC HAZARDS: MRI utilizes a powerful 1.5T/3.0T magnetic field. Absolute contraindications include non-MRI-compatible cardiac pacemakers, cerebral aneurysm clips, metallic intraocular foreign bodies, and cochlear implants. All metallic jewelry, piercings, hearing aids, cell phones, bank cards, and hairpins must be secured in clinic lockers.
2. RENAL ASSESSMENT FOR CONTRAST: If intravenous Gadolinium-based contrast agent is prescribed, a recent Serum Creatinine and eGFR report (conducted within the preceding 30 days) is mandatory to rule out nephrogenic systemic fibrosis.
3. FASTING STATUS: Fast for 4 hours prior to contrast injection to prevent contrast-induced nausea or emesis.
4. METFORMIN WARNING: Diabetic patients taking Metformin must consult their prescribing physician regarding withholding the drug for 48 hours post-contrast administration.""",
        simplifiedTextEn = """SIMPLE EXPLANATION FOR YOUR MRI SCAN:
• Powerful Magnet Safety: The MRI scanner is a huge, powerful magnet. You CANNOT have any metal on or inside your body. Tell the doctor immediately if you have a heart pacemaker, metal clips, or cochlear ear implants. Remove all coins, jewelry, belts, and bank cards.
• Kidney Function Test (Creatinine): The doctor may inject a safe medical dye (contrast) into your vein to make scans crystal clear. You must bring a recent kidney blood test (Creatinine report) to ensure your kidneys can filter this dye safely.
• 4-Hour Fast: Do not eat for 4 hours before the scan to prevent feeling nauseated from the contrast dye.
• Claustrophobia / Loud Sounds: The machine makes knocking and tapping sounds (earplugs are provided). If you feel afraid of enclosed spaces, notify the staff before starting.""",
        simplifiedTextTa = """உங்கள் எம்.ஆர்.ஐ ஸ்கேனுக்கான எளிய விளக்கம்:
• காந்தப்புல பாதுகாப்பு: எம்.ஆர்.ஐ இயந்திரம் ஒரு மிகப்பெரிய சக்திவாய்ந்த காந்தம். இதய பேஸ்மேக்கர், உலோக கிளிப்புகள் உங்கள் உடலில் இருந்தால் உடனே மருத்துவரிடம் தெரிவிக்கவும். நகைகள், நாணயங்கள், பெல்ட் போன்ற அனைத்து உலோகப் பொருட்களையும் அகற்ற வேண்டும்.
• சிறுநீரக இரத்த பரிசோதனை (Creatinine): ஸ்கேனில் தெளிவான படம் கிடைக்க நரம்பு வழியாக சாயம் (Contrast) செலுத்தப்படும். இதை உங்கள் சிறுநீரகம் பாதுகாப்பாக வெளியேற்ற முடியுமா என்பதை அறிய அண்மைய சீரம் கிரியேட்டினின் பரிசோதனை அறிக்கை அவசியம்.
• 4 மணி நேர விரதம்: சாயம் செலுத்தும் போது குமட்டல் வராமல் இருக்க 4 மணி நேரம் முன் சாப்பிடக்கூடாது.
• இயந்திர சத்தம்: இயந்திரம் தட்டும் சத்தம் எழுப்பும். காது அடைப்பான்கள் வழங்கப்படும்.""",
        criticalPoints = listOf(
            CriticalPoint(
                titleEn = "Remove All Metal & Report Implants",
                titleTa = "உலோகப் பொருட்களை அகற்றவும்",
                descriptionEn = "Strictly inform staff of pacemakers, metal clips, or pins. Remove belts, jewelry, and cards.",
                descriptionTa = "பேஸ்மேக்கர் போன்ற உலோக சாதனங்கள் உடலில் இருந்தால் தெரிவிக்கவும். நகைகளை கழற்றி வைக்கவும்.",
                isUrgent = true
            ),
            CriticalPoint(
                titleEn = "Recent Kidney Test Report Needed",
                titleTa = "சிறுநீரக பரிசோதனை அறிக்கை தேவை",
                descriptionEn = "Bring Serum Creatinine blood report from the past 30 days before receiving contrast dye.",
                descriptionTa = "சாயம் ஏற்றுவதற்கு முன் அண்மைய சிறுநீரக பரிசோதனை அறிக்கையை சமர்ப்பிக்க வேண்டும்.",
                isUrgent = true
            )
        ),
        glossary = listOf(
            GlossaryTerm(
                term = "Contrast Agent (Gadolinium)",
                definitionEn = "A safe dye injected into a vein that highlights blood vessels and soft tissues on scans.",
                definitionTa = "திசுக்கள் மற்றும் இரத்த நாளங்களை ஸ்கேனில் பளிச்சென காட்ட நரம்பில் செலுத்தப்படும் சாயம்.",
                clinicalContext = "Filtered naturally by healthy kidneys within a few hours."
            ),
            GlossaryTerm(
                term = "Pacemaker",
                definitionEn = "An electrical device implanted near the heart to regulate heartbeat.",
                definitionTa = "இதயத் துடிப்பை சீராக்க பொருத்தப்படும் சிறிய மின்னணு சாதனம்.",
                clinicalContext = "Strong MRI magnets can disrupt electronic pacemakers if they are not certified MRI-safe."
            )
        ),
        checklist = listOf(
            ChecklistItem(
                id = "mri_1",
                titleEn = "Get Serum Creatinine kidney blood test done and collect physical report",
                titleTa = "சிறுநீரக சீரம் கிரியேட்டினின் இரத்த பரிசோதனை செய்து அறிக்கையை வாங்கவும்",
                category = "Days Before",
                categoryTa = "நாட்களுக்கு முன்",
                dueTimingEn = "3 Days Before",
                dueTimingTa = "3 நாட்களுக்கு முன்",
                isCompleted = true
            ),
            ChecklistItem(
                id = "mri_2",
                titleEn = "Stop eating solid food 4 hours before your MRI appointment",
                titleTa = "எம்.ஆர்.ஐ பரிசோதனைக்கு 4 மணி நேரம் முன்பு உணவு உண்பதை நிறுத்தவும்",
                category = "Morning Of",
                categoryTa = "பரிசோதனை காலை",
                dueTimingEn = "4 Hours Prior",
                dueTimingTa = "4 மணி நேரம் முன்",
                isCompleted = false
            ),
            ChecklistItem(
                id = "mri_3",
                titleEn = "Leave all gold jewelry, metal hairpins, smartwatches, and credit cards at home",
                titleTa = "நகைகள், உலோக ஊசிகள், வாட்ச் மற்றும் வங்கி கார்டுகளை வீட்டிலேயே வைக்கவும்",
                category = "What to Bring",
                categoryTa = "எடுத்து வர வேண்டியவை",
                dueTimingEn = "Before Leaving Home",
                dueTimingTa = "வீட்டில் இருந்து கிளம்பும் முன்",
                isCompleted = false
            )
        ),
        fastingHours = 4,
        chaperoneRequired = false
    )

    val allSampleInstructions: List<MedicalInstructionDoc> = listOf(
        endoscopyInstruction,
        colonoscopyInstruction,
        bloodSugarInstruction,
        ultrasoundInstruction,
        mriInstruction
    )
}
