package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.ui.components.AudioPlayerBar
import com.example.ui.components.MedicalSafetyBanner
import com.example.ui.viewmodel.PrepSureUiState
import com.example.ui.viewmodel.ScreenTab
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    state: PrepSureUiState,
    isSpeaking: Boolean,
    onNavigate: (ScreenTab) -> Unit,
    onPlayTts: () -> Unit,
    onStopTts: () -> Unit,
    onCallProvider: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isTamil = state.currentLanguage == AppLanguage.TAMIL
    val totalTasks = state.checklistItems.size
    val completedTasks = state.checklistItems.count { it.isCompleted }
    val progressPercent = if (totalTasks > 0) (completedTasks * 100 / totalTasks) else 100

    val dateFormat = SimpleDateFormat("EEE, dd MMM • hh:mm a", Locale.getDefault())
    val apptDateFormatted = dateFormat.format(Date(state.appointment.scheduledDateEpoch))

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

        // Voice Assistant Bar
        item {
            AudioPlayerBar(
                isSpeaking = isSpeaking,
                onPlay = onPlayTts,
                onStop = onStopTts,
                language = state.currentLanguage
            )
        }

        // Patient Welcome & Readiness Gauge Card
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("patient_welcome_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isTamil) "வணக்கம், ${state.patientProfile.name}" else "Namaste, ${state.patientProfile.name}",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${state.patientProfile.age} yrs • Caregiver: ${state.patientProfile.caregiverName}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Readiness Badge
                        Surface(
                            shape = CircleShape,
                            color = if (progressPercent >= 100) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "$progressPercent%",
                                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Progress bar
                    LinearProgressIndicator(
                        progress = { progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isTamil) "$completedTasks / $totalTasks பணிகள் முடிந்தது" else "$completedTasks of $totalTasks preparation tasks ready",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = if (progressPercent >= 100) (if (isTamil) "தயாராக உள்ளது ✓" else "Fully Prepared ✓") else (if (isTamil) "நடவடிக்கையில்..." else "In Progress"),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (progressPercent >= 100) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Upcoming Screening Appointment Card
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(ScreenTab.APPOINTMENTS) }
                    .testTag("upcoming_appointment_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Event,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = if (isTamil) "அடுத்த பரிசோதனை" else "Upcoming Screening",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        AssistChip(
                            onClick = { onNavigate(ScreenTab.APPOINTMENTS) },
                            label = { Text("Report") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Print,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isTamil) state.appointment.procedureNameTa else state.appointment.procedureNameEn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${state.appointment.hospitalName} • ${state.appointment.doctorName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "📅 $apptDateFormatted (${state.appointment.department})",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Active Instruction Overview Tile
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(ScreenTab.SIMPLIFIER) }
                    .testTag("active_instruction_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isTamil) "மருத்துவ வழிகாட்டுதல் சுருக்கம்" else "Active Procedure Guide",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "View Details",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isTamil) state.activeInstruction.titleTa else state.activeInstruction.titleEn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Fasting & Escort tags
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (state.activeInstruction.fastingHours > 0) {
                            SuggestionChip(
                                onClick = {},
                                label = {
                                    Text(
                                        if (isTamil) "${state.activeInstruction.fastingHours} மணி நேர விரதம்" else "${state.activeInstruction.fastingHours}-hr Fasting",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer,
                                    labelColor = MaterialTheme.colorScheme.onErrorContainer
                                )
                            )
                        }
                        if (state.activeInstruction.chaperoneRequired) {
                            SuggestionChip(
                                onClick = {},
                                label = {
                                    Text(
                                        if (isTamil) "துணைவர் தேவை" else "Escort Required",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            )
                        }
                    }
                }
            }
        }

        // Quick Navigation Grid Tiles
        item {
            Text(
                text = if (isTamil) "அனைத்து தொகுதிகள்" else "Main Modules",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ModuleNavCard(
                        title = if (isTamil) "ஆவண பதிவேற்றம் (OCR)" else "Upload & OCR",
                        subtitle = if (isTamil) "மருத்துவ சீட்டை ஸ்கேன் செய்க" else "Scan prescription sheet",
                        icon = Icons.Default.DocumentScanner,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenTab.UPLOAD_OCR) },
                        testTag = "nav_upload_ocr"
                    )
                    ModuleNavCard(
                        title = if (isTamil) "எளிய வழிகாட்டி (AI)" else "AI Simplifier",
                        subtitle = if (isTamil) "கலைச்சொல் & விளக்கம்" else "Plain words & glossary",
                        icon = Icons.Default.AutoAwesome,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenTab.SIMPLIFIER) },
                        testTag = "nav_simplifier"
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ModuleNavCard(
                        title = if (isTamil) "ஸ்மார்ட் சரிபார்ப்பு பட்டியல்" else "Smart Checklist",
                        subtitle = if (isTamil) "$completedTasks/$totalTasks முடிந்தது" else "$completedTasks/$totalTasks completed",
                        icon = Icons.Default.CheckCircleOutline,
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenTab.CHECKLIST) },
                        testTag = "nav_checklist"
                    )
                    ModuleNavCard(
                        title = if (isTamil) "பரிசோதனை அறிக்கை" else "Print Preparation",
                        subtitle = if (isTamil) "அறிக்கை அச்சிட & பகிர" else "Printable PDF report",
                        icon = Icons.Default.LocalPrintshop,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenTab.APPOINTMENTS) },
                        testTag = "nav_appointments"
                    )
                }

                ModuleNavCard(
                    title = if (isTamil) "அணுகல்தன்மை & உதவி நுட்பங்கள்" else "Accessibility & Assistive Tech",
                    subtitle = if (isTamil) "பெரிய எழுத்து, அதி-தெளிவு நிறம் & குரல் உதவி" else "Large text mode, high contrast & voice reading",
                    icon = Icons.Default.AccessibilityNew,
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigate(ScreenTab.ACCESSIBILITY) },
                    testTag = "nav_accessibility"
                )
            }
        }
    }
}

@Composable
fun ModuleNavCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    containerColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
