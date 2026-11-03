package com.example.util

import android.content.Context
import android.content.Intent
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.data.model.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ReportUtils {

    fun generatePreparationReport(
        patient: PatientProfile,
        instruction: MedicalInstructionDoc,
        appointment: ScreeningAppointment,
        checklist: List<ChecklistItem>,
        language: AppLanguage
    ): String {
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy, hh:mm a", Locale.getDefault())
        val apptDate = dateFormat.format(Date(appointment.scheduledDateEpoch))

        val completedCount = checklist.count { it.isCompleted }
        val totalCount = checklist.size
        val readinessPercent = if (totalCount > 0) (completedCount * 100 / totalCount) else 100

        val isTamil = language == AppLanguage.TAMIL

        return buildString {
            append("=========================================\n")
            append("PREPSURE AI - PATIENT PREPARATION REPORT\n")
            append("Swasth & Samavesh Bharat Initiative\n")
            append("=========================================\n\n")

            append("PATIENT DETAILS:\n")
            append("• Name: ${patient.name}\n")
            append("• Age: ${patient.age} years\n")
            append("• Known Conditions/Allergies: ${patient.knownConditions}\n")
            append("• Primary Clinic: ${patient.clinicName}\n")
            append("• Caregiver / Escort: ${patient.caregiverName} (${patient.caregiverPhone})\n\n")

            append("UPCOMING SCREENING APPOINTMENT:\n")
            append("• Procedure: ${if (isTamil) appointment.procedureNameTa else appointment.procedureNameEn}\n")
            append("• Facility: ${appointment.hospitalName}\n")
            append("• Doctor: ${appointment.doctorName}\n")
            append("• Department: ${appointment.department}\n")
            append("• Scheduled Date & Time: $apptDate\n\n")

            append("READINESS STATUS: $readinessPercent% COMPLETE ($completedCount/$totalCount tasks done)\n\n")

            append("CRITICAL PREPARATION INSTRUCTIONS:\n")
            if (instruction.fastingHours > 0) {
                append("• Fasting: Strict ${instruction.fastingHours}-hour fast required.\n")
            }
            if (instruction.chaperoneRequired) {
                append("• Escort: An adult companion is mandatory due to relaxing sedation.\n")
            }
            instruction.criticalPoints.forEach { pt ->
                append("• [!] ${if (isTamil) pt.titleTa else pt.titleEn}: ${if (isTamil) pt.descriptionTa else pt.descriptionEn}\n")
            }
            append("\n")

            append("CHECKLIST STATUS:\n")
            checklist.forEach { item ->
                val mark = if (item.isCompleted) "[✓ COMPLETED]" else "[ ] PENDING"
                val title = if (isTamil) item.titleTa else item.titleEn
                val timing = if (isTamil) item.dueTimingTa else item.dueTimingEn
                append("$mark $title ($timing)\n")
            }
            append("\n")

            append("QUESTIONS TO ASK YOUR HEALTHCARE PROVIDER:\n")
            append("1. When can I resume my regular blood pressure and diabetes medicines?\n")
            append("2. When will the screening report / biopsy results be available?\n")
            append("3. What post-procedure symptoms require calling the clinic emergency number?\n\n")

            append("-----------------------------------------\n")
            append("SAFETY DISCLAIMER:\n")
            append("PrepSure AI assists in patient understanding. All instructions are derived from your doctor's sheet. If any instructions remain unclear, contact ${patient.clinicName} or emergency services (${patient.emergencyPhone}) immediately.\n")
            append("Generated on: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())}\n")
            append("=========================================\n")
        }
    }

    fun shareReport(context: Context, reportText: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, reportText)
            putExtra(Intent.EXTRA_SUBJECT, "PrepSure AI - Screening Preparation Report")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share Preparation Report")
        context.startActivity(shareIntent)
    }

    fun printReport(
        context: Context,
        patient: PatientProfile,
        instruction: MedicalInstructionDoc,
        appointment: ScreeningAppointment,
        checklist: List<ChecklistItem>,
        language: AppLanguage
    ) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager ?: return

        val isTamil = language == AppLanguage.TAMIL
        val apptDate = SimpleDateFormat("EEE, dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(appointment.scheduledDateEpoch))
        val completedCount = checklist.count { it.isCompleted }
        val totalCount = checklist.size
        val readinessPercent = if (totalCount > 0) (completedCount * 100 / totalCount) else 100

        val htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="utf-8">
                <style>
                    body { font-family: sans-serif; margin: 24px; color: #1e293b; line-height: 1.5; }
                    .header { border-bottom: 3px solid #0284c7; padding-bottom: 12px; margin-bottom: 20px; }
                    .title { font-size: 24px; font-weight: bold; color: #0369a1; }
                    .subtitle { font-size: 14px; color: #64748b; }
                    .section { margin-bottom: 18px; }
                    .section-title { font-size: 16px; font-weight: bold; color: #0284c7; border-bottom: 1px solid #e2e8f0; padding-bottom: 4px; margin-bottom: 8px; }
                    .box { background: #f8fafc; border: 1px solid #cbd5e1; border-radius: 6px; padding: 12px; margin-bottom: 12px; }
                    .urgent { background: #fef2f2; border: 1px solid #fecaca; color: #991b1b; padding: 8px; border-radius: 4px; margin-bottom: 6px; }
                    .badge { display: inline-block; padding: 4px 10px; border-radius: 12px; font-size: 13px; font-weight: bold; background: #e0f2fe; color: #0369a1; }
                    .item { margin-bottom: 6px; }
                    .checked { color: #059669; font-weight: bold; }
                    .pending { color: #d97706; }
                    .footer { font-size: 11px; color: #94a3b8; border-top: 1px solid #e2e8f0; padding-top: 10px; margin-top: 24px; }
                </style>
            </head>
            <body>
                <div class="header">
                    <div class="title">PREPSURE AI - Patient Preparation Report</div>
                    <div class="subtitle">Swasth & Samavesh Bharat • Healthcare Accessibility</div>
                </div>

                <div class="section">
                    <div class="section-title">Patient & Clinic Information</div>
                    <div class="box">
                        <strong>Patient:</strong> ${patient.name} (${patient.age} yrs) &bull;
                        <strong>Known Conditions:</strong> ${patient.knownConditions}<br>
                        <strong>Escort/Caregiver:</strong> ${patient.caregiverName} (${patient.caregiverPhone})<br>
                        <strong>Hospital/Facility:</strong> ${appointment.hospitalName} &bull; ${appointment.doctorName}
                    </div>
                </div>

                <div class="section">
                    <div class="section-title">Screening Appointment</div>
                    <div class="box">
                        <span class="badge">${if (isTamil) appointment.procedureNameTa else appointment.procedureNameEn}</span><br>
                        <strong>Date & Time:</strong> $apptDate<br>
                        <strong>Department:</strong> ${appointment.department}<br>
                        <strong>Preparation Readiness:</strong> <strong>$readinessPercent%</strong> ($completedCount / $totalCount completed)
                    </div>
                </div>

                <div class="section">
                    <div class="section-title">Critical Safety Instructions</div>
                    ${instruction.criticalPoints.joinToString("") { pt ->
                        "<div class='urgent'><strong>" + (if (isTamil) pt.titleTa else pt.titleEn) + ":</strong> " + (if (isTamil) pt.descriptionTa else pt.descriptionEn) + "</div>"
                    }}
                </div>

                <div class="section">
                    <div class="section-title">Preparation Checklist Status</div>
                    ${checklist.joinToString("") { item ->
                        val statusClass = if (item.isCompleted) "checked" else "pending"
                        val icon = if (item.isCompleted) "&#10003;" else "&#9633;"
                        "<div class='item $statusClass'>$icon " + (if (isTamil) item.titleTa else item.titleEn) + " <span style='color:#64748b; font-size:12px;'>(" + (if (isTamil) item.dueTimingTa else item.dueTimingEn) + ")</span></div>"
                    }}
                </div>

                <div class="footer">
                    Safety Notice: PrepSure AI is an assistive healthcare tool. No medical instructions were modified or invented. Always confirm with your physician if anything is unclear.
                </div>
            </body>
            </html>
        """.trimIndent()

        val webView = WebView(context)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                val printAdapter = webView.createPrintDocumentAdapter("PrepSure_Report")
                printManager.print("PrepSure_Report", printAdapter, PrintAttributes.Builder().build())
            }
        }
        webView.loadDataWithBaseURL(null, htmlContent, "text/HTML", "UTF-8", null)
    }
}
