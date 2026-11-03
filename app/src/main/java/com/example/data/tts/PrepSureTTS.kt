package com.example.data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class PrepSureTTS(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _currentUtterance = MutableStateFlow("")
    val currentUtterance: StateFlow<String> = _currentUtterance.asStateFlow()

    private val _speechRate = MutableStateFlow(0.9f) // Slightly slower, clear for healthcare clarity
    val speechRate: StateFlow<Float> = _speechRate.asStateFlow()

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            tts?.setSpeechRate(_speechRate.value)
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                    _currentUtterance.value = ""
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                    _currentUtterance.value = ""
                }
            })
        } else {
            Log.e("PrepSureTTS", "TextToSpeech initialization failed with code $status")
        }
    }

    fun speak(text: String, languageCode: String = "en") {
        if (!isInitialized || tts == null) return

        stop()
        _currentUtterance.value = text

        val locale = if (languageCode.equals("ta", ignoreCase = true)) {
            Locale("ta", "IN")
        } else {
            Locale.US
        }

        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            // Fallback to default locale if Tamil engine data is missing on system
            tts?.language = Locale.getDefault()
        }

        tts?.setSpeechRate(_speechRate.value)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "prepsure_utt_${System.currentTimeMillis()}")
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        _currentUtterance.value = ""
    }

    fun setSpeechRate(rate: Float) {
        val clamped = rate.coerceIn(0.5f, 1.5f)
        _speechRate.value = clamped
        tts?.setSpeechRate(clamped)
    }

    fun shutdown() {
        stop()
        tts?.shutdown()
        tts = null
    }
}
