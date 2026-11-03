package com.example.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.PatientProfile
import com.example.data.model.ScreeningAppointment
import com.example.ui.components.MedicalSafetyBanner
import com.example.ui.viewmodel.PrepSureUiState
import com.example.util.ReportUtils
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AppointmentScreen(
    state: PrepSureUiState,
    onUpdateAppointment: (ScreeningAppointment) -> Unit,
    onUpdateProfile: (PatientProfile) -> Unit,
    onCallProvider: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isTamil = state.currentLanguage == AppLanguage.TAMIL
    val appointment = state.appointment

    val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy • hh:mm a", Locale.getDefault())
    val apptDateFormatted = dateFormat.format(Date(appointment.scheduledDateEpoch))

    // Time remaining calculation
    val diffMillis = appointment.scheduledDateEpoch - System.currentTimeMillis()
    val daysRemaining = (diffMillis / (1000 * 60 * 60 * 24)).coerceAtLeast(0)
    val hoursRemaining = ((diffMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)).coerceAtLeast(0)

    var showEditDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        // Medical Safety Warning
        item {
            MedicalSafetyBanner(language = state.currentLanguage, onCallProvider = onCallProvider)
        }

        // Header Title
        item {
            Column {
                Text(
                    text = if (isTamil) "பரிசோதனை தயாரிப்பு & அறிக்கை" else "Appointment Preparation & Report",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (isTamil)
                        "பரிசோதனை விபரங்களை மேலாண்மை செய்து, மருத்துவமனைக்கான அறிக்கையை அச்சிடுங்கள்"
                    else
                        "Manage screening schedule and generate your official printable preparation report",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Appointment Countdown Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().testTag("appointment_detail_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = if (daysRemaining > 0) "$daysRemaining days $hoursRemaining hrs left" else "$hoursRemaining hrs remaining",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        IconButton(onClick = { showEditDialog = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Schedule", tint = MaterialTheme.colorScheme.primary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isTamil) appointment.procedureNameTa else appointment.procedureNameEn,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "📅 $apptDateFormatted",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "🏥 ${appointment.hospitalName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "👨‍⚕️ ${appointment.doctorName} • ${appointment.department}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    if (appointment.notes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "📝 Notes: ${appointment.notes}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        // Print & Share Report Action Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Print, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isTamil) "மருத்துவமனைக்கான தயாரிப்பு அறிக்கை" else "Printable Preparation Report",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isTamil) "PDF கோப்பாக சேமிக்க அல்லது அச்சிட" else "Generate official summary for doctors and nurses",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                ReportUtils.printReport(
                                    context = context,
                                    patient = state.patientProfile,
                                    instruction = state.activeInstruction,
                                    appointment = state.appointment,
                                    checklist = state.checklistItems,
                                    language = state.currentLanguage
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("print_report_button")
                        ) {
                            Icon(Icons.Default.LocalPrintshop, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isTamil) "அச்சிடுக / PDF" else "Print / PDF")
                        }

                        OutlinedButton(
                            onClick = {
                                val reportText = ReportUtils.generatePreparationReport(
                                    patient = state.patientProfile,
                                    instruction = state.activeInstruction,
                                    appointment = state.appointment,
                                    checklist = state.checklistItems,
                                    language = state.currentLanguage
                                )
                                ReportUtils.shareReport(context, reportText)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("share_report_button")
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isTamil) "பகிர்" else "Share")
                        }
                    }
                }
            }
        }

        // Patient Profile & Caregiver Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (isTamil) "நோயாளி & துணைவர் விபரங்கள்" else "Patient & Caregiver Details",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "• Patient: ${state.patientProfile.name} (${state.patientProfile.age} yrs)", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "• Caregiver/Escort: ${state.patientProfile.caregiverName} (${state.patientProfile.caregiverPhone})", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "• Medical Facility: ${state.patientProfile.clinicName}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "• Known Conditions: ${state.patientProfile.knownConditions}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    // Edit Appointment Dialog
    if (showEditDialog) {
        var doctorInput by remember { mutableStateOf(appointment.doctorName) }
        var hospitalInput by remember { mutableStateOf(appointment.hospitalName) }
        var notesInput by remember { mutableStateOf(appointment.notes) }

        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text(if (isTamil) "பரிசோதனை விபரங்களை திருத்து" else "Edit Appointment") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = hospitalInput,
                        onValueChange = { hospitalInput = it },
                        label = { Text("Hospital / Clinic") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = doctorInput,
                        onValueChange = { doctorInput = it },
                        label = { Text("Doctor Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = notesInput,
                        onValueChange = { notesInput = it },
                        label = { Text("Special Preparation Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateAppointment(
                            appointment.copy(
                                hospitalName = hospitalInput,
                                doctorName = doctorInput,
                                notes = notesInput
                            )
                        )
                        showEditDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
