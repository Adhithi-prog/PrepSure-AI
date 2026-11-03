package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.AppLanguage
import com.example.ui.components.PrepSureTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.PrepSureTheme
import com.example.ui.viewmodel.PrepSureViewModel
import com.example.ui.viewmodel.ScreenTab

class MainActivity : ComponentActivity() {

    private val viewModel: PrepSureViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val isSpeaking by viewModel.tts.isSpeaking.collectAsStateWithLifecycle()

            // Handle Large Text accessibility mode
            val baseDensity = LocalDensity.current
            val adjustedDensity = remember(uiState.accessibility.isLargeText, baseDensity) {
                if (uiState.accessibility.isLargeText) {
                    Density(
                        density = baseDensity.density,
                        fontScale = baseDensity.fontScale * 1.25f
                    )
                } else {
                    baseDensity
                }
            }

            CompositionLocalProvider(LocalDensity provides adjustedDensity) {
                PrepSureTheme(
                    highContrastMode = uiState.accessibility.isHighContrast
                ) {
                    val isTamil = uiState.currentLanguage == AppLanguage.TAMIL

                    // BackHandler returns to DASHBOARD if on a secondary screen
                    BackHandler(enabled = uiState.currentTab != ScreenTab.DASHBOARD) {
                        viewModel.selectTab(ScreenTab.DASHBOARD)
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets.safeDrawing,
                        topBar = {
                            PrepSureTopAppBar(
                                title = "PrepSure AI",
                                currentLanguage = uiState.currentLanguage,
                                isSpeaking = isSpeaking,
                                onToggleLanguage = { viewModel.toggleLanguage() },
                                onToggleTts = {
                                    if (isSpeaking) {
                                        viewModel.stopSpeaking()
                                    } else {
                                        viewModel.speakActiveInstruction()
                                    }
                                },
                                onOpenAccessibility = { viewModel.selectTab(ScreenTab.ACCESSIBILITY) }
                            )
                        },
                        bottomBar = {
                            NavigationBar(
                                modifier = Modifier
                                    .windowInsetsPadding(WindowInsets.navigationBars)
                                    .testTag("main_navigation_bar")
                            ) {
                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.DASHBOARD,
                                    onClick = { viewModel.selectTab(ScreenTab.DASHBOARD) },
                                    icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                                    label = { Text(if (isTamil) "முகப்பு" else "Home") },
                                    modifier = Modifier.testTag("nav_item_dashboard")
                                )
                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.UPLOAD_OCR,
                                    onClick = { viewModel.selectTab(ScreenTab.UPLOAD_OCR) },
                                    icon = { Icon(Icons.Default.DocumentScanner, contentDescription = "Upload") },
                                    label = { Text(if (isTamil) "ஸ்கேன்" else "Upload") },
                                    modifier = Modifier.testTag("nav_item_upload")
                                )
                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.SIMPLIFIER,
                                    onClick = { viewModel.selectTab(ScreenTab.SIMPLIFIER) },
                                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Guide") },
                                    label = { Text(if (isTamil) "வழிகாட்டி" else "Guide") },
                                    modifier = Modifier.testTag("nav_item_simplifier")
                                )
                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.CHECKLIST,
                                    onClick = { viewModel.selectTab(ScreenTab.CHECKLIST) },
                                    icon = { Icon(Icons.Default.Checklist, contentDescription = "Checklist") },
                                    label = { Text(if (isTamil) "பட்டியல்" else "Checklist") },
                                    modifier = Modifier.testTag("nav_item_checklist")
                                )
                                NavigationBarItem(
                                    selected = uiState.currentTab == ScreenTab.APPOINTMENTS,
                                    onClick = { viewModel.selectTab(ScreenTab.APPOINTMENTS) },
                                    icon = { Icon(Icons.Default.LocalPrintshop, contentDescription = "Report") },
                                    label = { Text(if (isTamil) "அறிக்கை" else "Report") },
                                    modifier = Modifier.testTag("nav_item_appointments")
                                )
                            }
                        }
                    ) { innerPadding ->
                        val contentModifier = Modifier.padding(innerPadding)

                        when (uiState.currentTab) {
                            ScreenTab.DASHBOARD -> {
                                DashboardScreen(
                                    state = uiState,
                                    isSpeaking = isSpeaking,
                                    onNavigate = { viewModel.selectTab(it) },
                                    onPlayTts = { viewModel.speakActiveInstruction() },
                                    onStopTts = { viewModel.stopSpeaking() },
                                    onCallProvider = { dialClinic(uiState.patientProfile.emergencyPhone) },
                                    modifier = contentModifier
                                )
                            }
                            ScreenTab.UPLOAD_OCR -> {
                                DocumentUploadScreen(
                                    state = uiState,
                                    isSpeaking = isSpeaking,
                                    onSelectInstruction = { viewModel.selectInstruction(it) },
                                    onProcessCustomDocument = { text, hint ->
                                        viewModel.processCustomDocument(text, hint)
                                    },
                                    onPlayTts = { text -> viewModel.speakText(text) },
                                    onStopTts = { viewModel.stopSpeaking() },
                                    modifier = contentModifier
                                )
                            }
                            ScreenTab.SIMPLIFIER -> {
                                SimplifierScreen(
                                    state = uiState,
                                    isSpeaking = isSpeaking,
                                    onNavigate = { viewModel.selectTab(it) },
                                    onPlayTts = { text -> viewModel.speakText(text) },
                                    onStopTts = { viewModel.stopSpeaking() },
                                    onToggleCompareMode = { viewModel.toggleCompareMode() },
                                    onCallProvider = { dialClinic(uiState.patientProfile.emergencyPhone) },
                                    modifier = contentModifier
                                )
                            }
                            ScreenTab.CHECKLIST -> {
                                ChecklistScreen(
                                    state = uiState,
                                    isSpeaking = isSpeaking,
                                    onToggleItem = { viewModel.toggleChecklistItem(it) },
                                    onResetChecklist = { viewModel.resetChecklist() },
                                    onNavigate = { viewModel.selectTab(it) },
                                    onPlayTts = { text -> viewModel.speakText(text) },
                                    onStopTts = { viewModel.stopSpeaking() },
                                    modifier = contentModifier
                                )
                            }
                            ScreenTab.APPOINTMENTS -> {
                                AppointmentScreen(
                                    state = uiState,
                                    onUpdateAppointment = { viewModel.updateAppointment(it) },
                                    onUpdateProfile = { viewModel.updateProfile(it) },
                                    onCallProvider = { dialClinic(uiState.patientProfile.emergencyPhone) },
                                    modifier = contentModifier
                                )
                            }
                            ScreenTab.ACCESSIBILITY -> {
                                AccessibilityScreen(
                                    state = uiState,
                                    isSpeaking = isSpeaking,
                                    onToggleLargeText = { viewModel.toggleLargeText(it) },
                                    onToggleHighContrast = { viewModel.toggleHighContrast(it) },
                                    onToggleSimpleNav = { viewModel.toggleSimpleNav(it) },
                                    onSetSpeechRate = { viewModel.setSpeechRate(it) },
                                    onTestTts = { msg -> viewModel.speakText(msg) },
                                    onStopTts = { viewModel.stopSpeaking() },
                                    modifier = contentModifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dialClinic(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        } catch (_: Exception) {
            // gracefully catch if dialer is not present
        }
    }
}
