package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.AppLanguage
import com.example.ui.viewmodel.PrepSureUiState

@Composable
fun AccessibilityScreen(
    state: PrepSureUiState,
    isSpeaking: Boolean,
    onToggleLargeText: (Boolean) -> Unit,
    onToggleHighContrast: (Boolean) -> Unit,
    onToggleSimpleNav: (Boolean) -> Unit,
    onSetSpeechRate: (Float) -> Unit,
    onTestTts: (String) -> Unit,
    onStopTts: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isTamil = state.currentLanguage == AppLanguage.TAMIL
    val access = state.accessibility

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        item {
            Column {
                Text(
                    text = if (isTamil) "அணுகல்தன்மை & உதவி நுட்பங்கள்" else "Accessibility & Assistive Tech",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (isTamil)
                        "முதியவர்கள் மற்றும் மாற்றுத்திறனாளிகளுக்கான எளிய மற்றும் பிரத்யேக அமைப்புகள்"
                    else
                        "Inclusive tools designed for elderly citizens, low-vision patients, and diverse linguistic needs",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Large Text Mode Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.FormatSize, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isTamil) "பெரிய எழுத்து முறை (Large Text)" else "Large Text Mode",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isTamil) "தெளிவாக படிக்க எழுத்துக்களின் அளவை பெரிதாக்குகிறது" else "Increases typography scale for effortless reading",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = access.isLargeText,
                        onCheckedChange = onToggleLargeText,
                        modifier = Modifier.testTag("toggle_large_text")
                    )
                }
            }
        }

        // High Contrast Mode Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Contrast, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isTamil) "அதி-தெளிவு நிற முறை (High Contrast)" else "High Contrast Mode",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isTamil) "கண்பார்வை குறைபாடு உள்ளவர்களுக்கான கூர்மையான கருப்பு-மஞ்சள் பின்னணி" else "Bold dark background with high-visibility borders",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = access.isHighContrast,
                        onCheckedChange = onToggleHighContrast,
                        modifier = Modifier.testTag("toggle_high_contrast")
                    )
                }
            }
        }

        // Simple Navigation Mode Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.TouchApp, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isTamil) "எளிய வழிசெலுத்தல் (Simple Navigation)" else "Simple Navigation Mode",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isTamil) "பெரிய தொடு பொத்தான்கள் மற்றும் எளிய வடிவமைப்பு" else "Extra-large buttons and streamlined layouts",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = access.isSimpleNavigation,
                        onCheckedChange = onToggleSimpleNav,
                        modifier = Modifier.testTag("toggle_simple_nav")
                    )
                }
            }
        }

        // Text-to-Speech Speed Adjuster Card
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
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Speed, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isTamil) "குரல் வாசிப்பு வேகம் (Speech Rate)" else "Voice Reading Speed (TTS)",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${String.format("%.1f", access.speechRate)}x (${if (access.speechRate < 0.9f) "Slow & Clear" else if (access.speechRate > 1.1f) "Fast" else "Normal"})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Slider(
                        value = access.speechRate,
                        onValueChange = onSetSpeechRate,
                        valueRange = 0.6f..1.4f,
                        steps = 7,
                        modifier = Modifier.testTag("tts_speed_slider")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val testMsg = if (isTamil)
                                    "வணக்கம். இது பிரெப்சூர் ஏஐ குரல் வாசிப்பு பரிசோதனை. மருத்துவ அறிவுரைகள் தெளிவாக கேட்கிறதா?"
                                else
                                    "Hello. This is PrepSure AI voice assistant. Are the medical instructions clear and comfortable?"
                                onTestTts(testMsg)
                            },
                            modifier = Modifier.weight(1f).testTag("test_voice_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isTamil) "குரல் சோதனை" else "Test Voice")
                        }

                        if (isSpeaking) {
                            OutlinedButton(
                                onClick = onStopTts,
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Stop, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Stop")
                            }
                        }
                    }
                }
            }
        }

        // Swasth & Samavesh Bharat Commitment Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "🇮🇳 Swasth & Samavesh Bharat Initiative",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "PrepSure AI bridges the critical healthcare communication gap across India. By offering multimodal simplifications, local language audio support in Tamil & English, high-contrast visual ergonomics, and verification quizzes, no patient is left unprepared or vulnerable before essential medical screenings.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
