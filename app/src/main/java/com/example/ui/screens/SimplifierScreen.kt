package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.GlossaryTerm
import com.example.ui.components.AudioPlayerBar
import com.example.ui.components.MedicalSafetyBanner
import com.example.ui.viewmodel.PrepSureUiState
import com.example.ui.viewmodel.ScreenTab

@Composable
fun SimplifierScreen(
    state: PrepSureUiState,
    isSpeaking: Boolean,
    onNavigate: (ScreenTab) -> Unit,
    onPlayTts: (String) -> Unit,
    onStopTts: () -> Unit,
    onToggleCompareMode: () -> Unit,
    onCallProvider: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isTamil = state.currentLanguage == AppLanguage.TAMIL
    val doc = state.activeInstruction
    var selectedGlossaryTerm by remember { mutableStateOf<GlossaryTerm?>(null) }
    var simplifierTab by remember { mutableIntStateOf(0) } // 0: Simple Guide, 1: Glossary, 2: Original Sheet

    val textToSpeak = if (isTamil) doc.simplifiedTextTa else doc.simplifiedTextEn

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        // Safety Banner
        item {
            MedicalSafetyBanner(language = state.currentLanguage, onCallProvider = onCallProvider)
        }

        // Header Title
        item {
            Column {
                Text(
                    text = if (isTamil) doc.titleTa else doc.titleEn,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${doc.doctorOrClinicName} • 100% Traceable to Clinical Sheet",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Audio Listen Bar
        item {
            AudioPlayerBar(
                isSpeaking = isSpeaking,
                onPlay = { onPlayTts(textToSpeak) },
                onStop = onStopTts,
                language = state.currentLanguage
            )
        }

        // Sub-tabs: Simplified Guide / Medical Glossary / Original Doctor Sheet
        item {
            TabRow(
                selectedTabIndex = simplifierTab,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                divider = {}
            ) {
                Tab(
                    selected = simplifierTab == 0,
                    onClick = { simplifierTab = 0 },
                    text = { Text(if (isTamil) "எளிய வழிகாட்டி" else "Simple Guide", style = MaterialTheme.typography.labelMedium) }
                )
                Tab(
                    selected = simplifierTab == 1,
                    onClick = { simplifierTab = 1 },
                    text = { Text(if (isTamil) "கலைச்சொற்கள்" else "Glossary (${doc.glossary.size})", style = MaterialTheme.typography.labelMedium) }
                )
                Tab(
                    selected = simplifierTab == 2,
                    onClick = { simplifierTab = 2 },
                    text = { Text(if (isTamil) "மூல ஆவணம்" else "Original Sheet", style = MaterialTheme.typography.labelMedium) }
                )
            }
        }

        if (simplifierTab == 0) {
            // Critical Highlight Points
            item {
                Text(
                    text = if (isTamil) "முக்கிய பாதுகாப்பு விதிகள்" else "Critical Highlights",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            items(doc.criticalPoints) { point ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (point.isUrgent) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = if (point.isUrgent) Icons.Default.PriorityHigh else Icons.Default.Info,
                            contentDescription = null,
                            tint = if (point.isUrgent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isTamil) point.titleTa else point.titleEn,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (isTamil) point.descriptionTa else point.descriptionEn,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Simplified Breakdown (6th-Grade Reading Level)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isTamil) "எளிய தமிழ் விளக்கம்" else "Plain Language Breakdown",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = if (isTamil) "6-ஆம் வகுப்பு நிலை" else "6th Grade Level",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val explanationText = if (isTamil) doc.simplifiedTextTa else doc.simplifiedTextEn
                        Text(
                            text = explanationText,
                            style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 26.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Traceability stamp
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isTamil) "அசல் மருத்துவ சீட்டின் அடிப்படையில் சரிபார்க்கப்பட்டது • தவறான தகவல்கள் சேர்க்கப்படவில்லை" else "Traceable directly to hospital sheet • Zero invented advice",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        } else if (simplifierTab == 1) {
            // Glossary List
            item {
                Text(
                    text = if (isTamil) "மருத்துவ கலைச்சொற்கள் அகராதி" else "Medical Term Glossary",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            items(doc.glossary) { term ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedGlossaryTerm = term }
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = term.term,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isTamil) term.definitionTa else term.definitionEn,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "💡 Clinical Purpose: ${term.clinicalContext}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            // Original Doctor Sheet View
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Article, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isTamil) "மருத்துவர் வழங்கிய மூல உரை" else "Original Clinical Text",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = doc.originalText,
                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, lineHeight = 20.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        // Action Buttons to next modules
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { onNavigate(ScreenTab.CHECKLIST) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("open_checklist_from_simplifier_button")
                ) {
                    Icon(imageVector = Icons.Default.Checklist, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isTamil) "ஸ்மார்ட் பட்டியல்" else "Smart Checklist")
                }

                FilledTonalButton(
                    onClick = { onNavigate(ScreenTab.APPOINTMENTS) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("open_appointments_from_simplifier_button")
                ) {
                    Icon(imageVector = Icons.Default.LocalPrintshop, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isTamil) "தயாரிப்பு அறிக்கை" else "Print Report")
                }
            }
        }
    }
}
