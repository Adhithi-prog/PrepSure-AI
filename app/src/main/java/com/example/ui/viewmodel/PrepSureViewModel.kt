package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.InstructionSimplifierEngine
import com.example.data.local.ChecklistProgressEntity
import com.example.data.local.PrepSureDatabase
import com.example.data.model.*
import com.example.data.sample.SampleMedicalInstructions
import com.example.data.tts.PrepSureTTS
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class ScreenTab {
    DASHBOARD,
    UPLOAD_OCR,
    SIMPLIFIER,
    CHECKLIST,
    APPOINTMENTS,
    ACCESSIBILITY
}

data class AccessibilityPreferences(
    val isLargeText: Boolean = false,
    val isHighContrast: Boolean = false,
    val isSimpleNavigation: Boolean = false,
    val speechRate: Float = 0.9f
)

data class PrepSureUiState(
    val currentTab: ScreenTab = ScreenTab.DASHBOARD,
    val currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    val activeInstruction: MedicalInstructionDoc = SampleMedicalInstructions.endoscopyInstruction,
    val checklistItems: List<ChecklistItem> = SampleMedicalInstructions.endoscopyInstruction.checklist,
    val patientProfile: PatientProfile = PatientProfile(),
    val appointment: ScreeningAppointment = ScreeningAppointment(),
    val accessibility: AccessibilityPreferences = AccessibilityPreferences(),
    val isProcessingOcr: Boolean = false,
    val ocrError: String? = null,
    val simplifierCompareMode: Boolean = false // false = simplified view, true = side-by-side / original
)

class PrepSureViewModel(application: Application) : AndroidViewModel(application) {

    private val db = PrepSureDatabase.getDatabase(application)
    val tts = PrepSureTTS(application)

    private val _uiState = MutableStateFlow(PrepSureUiState())
    val uiState: StateFlow<PrepSureUiState> = _uiState.asStateFlow()

    init {
        loadPersistedChecklist(SampleMedicalInstructions.endoscopyInstruction.id)
    }

    fun selectTab(tab: ScreenTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun toggleLanguage() {
        val nextLang = if (_uiState.value.currentLanguage == AppLanguage.ENGLISH) {
            AppLanguage.TAMIL
        } else {
            AppLanguage.ENGLISH
        }
        _uiState.update { it.copy(currentLanguage = nextLang) }
    }

    fun setLanguage(lang: AppLanguage) {
        _uiState.update { it.copy(currentLanguage = lang) }
    }

    fun selectInstruction(instruction: MedicalInstructionDoc) {
        tts.stop()
        _uiState.update {
            it.copy(
                activeInstruction = instruction,
                checklistItems = instruction.checklist
            )
        }
        loadPersistedChecklist(instruction.id)
    }

    fun toggleChecklistItem(itemId: String) {
        val currentItems = _uiState.value.checklistItems.toMutableList()
        val index = currentItems.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = currentItems[index]
            val updatedItem = item.copy(
                isCompleted = !item.isCompleted,
                completedAt = if (!item.isCompleted) System.currentTimeMillis() else null
            )
            currentItems[index] = updatedItem
            _uiState.update { it.copy(checklistItems = currentItems) }

            // Haptic vibration feedback
            triggerVibration()

            // Persist to Room
            viewModelScope.launch {
                db.checklistDao().setChecklistItemStatus(
                    ChecklistProgressEntity(
                        instructionId = _uiState.value.activeInstruction.id,
                        itemId = itemId,
                        isCompleted = updatedItem.isCompleted,
                        completedAtEpoch = updatedItem.completedAt
                    )
                )
            }
        }
    }

    fun resetChecklist() {
        val currentItems = _uiState.value.checklistItems.map { it.copy(isCompleted = false, completedAt = null) }
        _uiState.update { it.copy(checklistItems = currentItems) }
        viewModelScope.launch {
            db.checklistDao().resetProgress(_uiState.value.activeInstruction.id)
        }
    }

    private fun loadPersistedChecklist(instructionId: String) {
        viewModelScope.launch {
            db.checklistDao().getProgressForInstruction(instructionId).collect { savedProgressList ->
                val progressMap = savedProgressList.associateBy { it.itemId }
                _uiState.update { state ->
                    val updated = state.checklistItems.map { item ->
                        val saved = progressMap[item.id]
                        if (saved != null) {
                            item.copy(isCompleted = saved.isCompleted, completedAt = saved.completedAtEpoch)
                        } else {
                            item
                        }
                    }
                    state.copy(checklistItems = updated)
                }
            }
        }
    }

    fun processCustomDocument(extractedText: String, procedureHint: String = "Uploaded Document") {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingOcr = true, ocrError = null) }
            try {
                val simplifiedDoc = InstructionSimplifierEngine.simplifyAndStructure(extractedText, procedureHint)
                _uiState.update {
                    it.copy(
                        isProcessingOcr = false,
                        activeInstruction = simplifiedDoc,
                        checklistItems = simplifiedDoc.checklist,
                        currentTab = ScreenTab.SIMPLIFIER
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isProcessingOcr = false, ocrError = "Could not process document. Using clinical rule engine.")
                }
            }
        }
    }

    fun updateProfile(profile: PatientProfile) {
        _uiState.update { it.copy(patientProfile = profile) }
    }

    fun updateAppointment(appointment: ScreeningAppointment) {
        _uiState.update { it.copy(appointment = appointment) }
    }

    fun toggleLargeText(enabled: Boolean) {
        _uiState.update { it.copy(accessibility = it.accessibility.copy(isLargeText = enabled)) }
    }

    fun toggleHighContrast(enabled: Boolean) {
        _uiState.update { it.copy(accessibility = it.accessibility.copy(isHighContrast = enabled)) }
    }

    fun toggleSimpleNav(enabled: Boolean) {
        _uiState.update { it.copy(accessibility = it.accessibility.copy(isSimpleNavigation = enabled)) }
    }

    fun setSpeechRate(rate: Float) {
        tts.setSpeechRate(rate)
        _uiState.update { it.copy(accessibility = it.accessibility.copy(speechRate = rate)) }
    }

    fun toggleCompareMode() {
        _uiState.update { it.copy(simplifierCompareMode = !it.simplifierCompareMode) }
    }

    fun speakActiveInstruction() {
        val state = _uiState.value
        val isTamil = state.currentLanguage == AppLanguage.TAMIL
        val textToRead = if (isTamil) state.activeInstruction.simplifiedTextTa else state.activeInstruction.simplifiedTextEn
        val langCode = if (isTamil) "ta" else "en"
        tts.speak(textToRead, langCode)
    }

    fun speakText(text: String) {
        val isTamil = _uiState.value.currentLanguage == AppLanguage.TAMIL
        val langCode = if (isTamil) "ta" else "en"
        tts.speak(text, langCode)
    }

    fun stopSpeaking() {
        tts.stop()
    }

    private fun triggerVibration() {
        try {
            val context = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(40)
                }
            }
        } catch (_: Exception) {
            // gracefully ignore if vibration service is unavailable
        }
    }

    override fun onCleared() {
        super.onCleared()
        tts.shutdown()
    }
}
