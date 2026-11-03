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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppLanguage
import com.example.data.model.ChecklistItem
import com.example.ui.components.AudioPlayerBar
import com.example.ui.viewmodel.PrepSureUiState
import com.example.ui.viewmodel.ScreenTab

@Composable
fun ChecklistScreen(
    state: PrepSureUiState,
    isSpeaking: Boolean,
    onToggleItem: (String) -> Unit,
    onResetChecklist: () -> Unit,
    onNavigate: (ScreenTab) -> Unit,
    onPlayTts: (String) -> Unit,
    onStopTts: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isTamil = state.currentLanguage == AppLanguage.TAMIL
    var filterType by remember { mutableIntStateOf(0) } // 0: All, 1: Pending, 2: Completed

    val totalCount = state.checklistItems.size
    val completedCount = state.checklistItems.count { it.isCompleted }
    val progressPercent = if (totalCount > 0) (completedCount * 100 / totalCount) else 100

    val filteredList = when (filterType) {
        1 -> state.checklistItems.filter { !it.isCompleted }
        2 -> state.checklistItems.filter { it.isCompleted }
        else -> state.checklistItems
    }

    // Group items by timeline category
    val groupedItems = filteredList.groupBy { if (isTamil) it.categoryTa else it.category }

    // Text summary for audio TTS
    val checklistSummary = buildString {
        append(if (isTamil) "தயாரிப்பு சரிபார்ப்பு பட்டியல். " else "Preparation checklist. ")
        append(if (isTamil) "$totalCount பணிகளில் $completedCount பணிகள் முடிந்துள்ளன. " else "$completedCount of $totalCount tasks completed. ")
        state.checklistItems.forEach { item ->
            val status = if (item.isCompleted) (if (isTamil) "முடிந்தது: " else "Completed: ") else (if (isTamil) "நிலுவை: " else "Pending: ")
            val title = if (isTamil) item.titleTa else item.titleEn
            val timing = if (isTamil) item.dueTimingTa else item.dueTimingEn
            append("$status $title ($timing). ")
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp)
    ) {
        // Audio assistant bar
        item {
            AudioPlayerBar(
                isSpeaking = isSpeaking,
                onPlay = { onPlayTts(checklistSummary) },
                onStop = onStopTts,
                language = state.currentLanguage
            )
        }

        // Progress Overview Card
        item {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isTamil) "தயாரிப்பு முன்னேற்றம்" else "Preparation Progress",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (isTamil) "$completedCount / $totalCount பணிகள் முடிக்கப்பட்டுள்ளது" else "$completedCount of $totalCount tasks completed",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = if (progressPercent >= 100) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(50.dp)
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

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { progressPercent / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

        // Celebration card when 100% ready
        if (progressPercent >= 100 && totalCount > 0) {
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isTamil) "வாழ்த்துகள்! நீங்கள் பரிசோதனைக்கு தயார்!" else "Outstanding! You are fully prepared!",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = if (isTamil) "அனைத்து தயாரிப்பு பணிகளும் வெற்றிகரமாக செய்யப்பட்டுள்ளன." else "All pre-procedure guidelines have been confirmed.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }
        }

        // Filter chips & Reset Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    FilterChip(
                        selected = filterType == 0,
                        onClick = { filterType = 0 },
                        label = { Text(if (isTamil) "அனைத்தும் ($totalCount)" else "All ($totalCount)") }
                    )
                    FilterChip(
                        selected = filterType == 1,
                        onClick = { filterType = 1 },
                        label = { Text(if (isTamil) "நிலுவை (${totalCount - completedCount})" else "Pending (${totalCount - completedCount})") }
                    )
                    FilterChip(
                        selected = filterType == 2,
                        onClick = { filterType = 2 },
                        label = { Text(if (isTamil) "முடிந்தது ($completedCount)" else "Done ($completedCount)") }
                    )
                }

                TextButton(onClick = onResetChecklist) {
                    Text(if (isTamil) "மீட்டமை" else "Reset", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // Checklist Items organized by timeline groups
        groupedItems.forEach { (categoryName, items) ->
            item {
                Text(
                    text = categoryName,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }

            items(items) { item ->
                ChecklistItemCard(
                    item = item,
                    isTamil = isTamil,
                    onToggle = { onToggleItem(item.id) },
                    onSpeak = {
                        val title = if (isTamil) item.titleTa else item.titleEn
                        val due = if (isTamil) item.dueTimingTa else item.dueTimingEn
                        onPlayTts("$title. $due.")
                    }
                )
            }
        }

        // Next Step CTA: Preparation Report
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { onNavigate(ScreenTab.APPOINTMENTS) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("view_report_cta_button")
            ) {
                Icon(imageVector = Icons.Default.LocalPrintshop, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isTamil) "அடுத்த கட்டம்: தயாரிப்பு அறிக்கையை அச்சிடுக / பகிர்க" else "Next Step: Print or Share Preparation Report")
            }
        }
    }
}

@Composable
fun ChecklistItemCard(
    item: ChecklistItem,
    isTamil: Boolean,
    onToggle: () -> Unit,
    onSpeak: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (item.isCompleted) MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onToggle() }
            .border(
                1.dp,
                if (item.isCompleted) MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                RoundedCornerShape(12.dp)
            )
            .testTag("checklist_item_${item.id}")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.testTag("checkbox_${item.id}")
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isTamil) item.titleTa else item.titleEn,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                        fontWeight = if (item.isCompleted) FontWeight.Normal else FontWeight.Medium
                    ),
                    color = if (item.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "⏰ ${if (isTamil) item.dueTimingTa else item.dueTimingEn}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(onClick = onSpeak) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Read item",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
