package com.example.data.model

enum class AppLanguage(val code: String, val displayName: String, val nativeName: String) {
    ENGLISH("en", "English", "English"),
    TAMIL("ta", "Tamil", "தமிழ்")
}

enum class ProcedureCategory(val titleEn: String, val titleTa: String) {
    ENDOSCOPY("Upper GI Endoscopy", "மேல் இரைப்பை எண்டோஸ்கோபி"),
    COLONOSCOPY("Colonoscopy Bowel Prep", "பெருங்குடல் பரிசோதனை தயாரிப்பு"),
    BLOOD_TEST("Fasting Blood Sugar & Lipid", "வெறும் வயிற்று இரத்த பரிசோதனை"),
    ULTRASOUND("Abdominal Ultrasound", "வயிற்று பகுதி அல்ட்ராசவுண்ட்"),
    MRI_SCAN("Contrast MRI Scan", "எம்.ஆர்.ஐ ஸ்கேன் (கான்ட்ராஸ்ட்)")
}

data class GlossaryTerm(
    val term: String,
    val definitionEn: String,
    val definitionTa: String,
    val clinicalContext: String
)

data class CriticalPoint(
    val titleEn: String,
    val titleTa: String,
    val descriptionEn: String,
    val descriptionTa: String,
    val isUrgent: Boolean = true
)

data class ChecklistItem(
    val id: String,
    val titleEn: String,
    val titleTa: String,
    val category: String, // "Days Before", "Night Before", "Morning Of", "What to Bring"
    val categoryTa: String,
    val dueTimingEn: String,
    val dueTimingTa: String,
    val isCompleted: Boolean = false,
    val completedAt: Long? = null
)

data class MedicalInstructionDoc(
    val id: String,
    val titleEn: String,
    val titleTa: String,
    val category: ProcedureCategory,
    val doctorOrClinicName: String,
    val originalText: String,
    val simplifiedTextEn: String,
    val simplifiedTextTa: String,
    val criticalPoints: List<CriticalPoint>,
    val glossary: List<GlossaryTerm>,
    val checklist: List<ChecklistItem>,
    val fastingHours: Int,
    val chaperoneRequired: Boolean,
    val uploadedAtEpoch: Long = System.currentTimeMillis()
)

data class PatientProfile(
    val name: String = "Kathir",
    val age: Int = 54,
    val preferredLanguage: AppLanguage = AppLanguage.ENGLISH,
    val caregiverName: String = "Karthi (Son)",
    val caregiverPhone: String = "+91 98765 43210",
    val clinicName: String = "National Health Care Centre, Chennai",
    val knownConditions: String = "Mild Hypertension, Penicillin Allergy",
    val emergencyPhone: String = "108"
)

data class ScreeningAppointment(
    val id: String = "appt_1",
    val procedureNameEn: String = "Upper GI Endoscopy",
    val procedureNameTa: String = "மேல் இரைப்பை எண்டோஸ்கோபி",
    val hospitalName: String = "Government Multi Super Speciality Hospital, Chennai",
    val doctorName: String = "Dr. K. Srinivasan, MD, DM (Gastro)",
    val scheduledDateEpoch: Long = System.currentTimeMillis() + (2 * 24 * 3600 * 1000L), // 2 days later
    val department: String = "Endoscopy Suite, 3rd Floor, Room 304",
    val notes: String = "Report 45 minutes prior. Bring prior ECG and prescription list."
)
