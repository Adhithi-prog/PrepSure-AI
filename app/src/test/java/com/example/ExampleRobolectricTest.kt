package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ai.InstructionSimplifierEngine
import com.example.data.sample.SampleMedicalInstructions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context matches app name`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("PrepSure AI", appName)
    }

    @Test
    fun `sample instructions repository has comprehensive procedures`() {
        val instructions = SampleMedicalInstructions.allSampleInstructions
        assertEquals(5, instructions.size)
        assertTrue(instructions.any { it.titleEn.contains("Endoscopy") })
        assertTrue(instructions.any { it.titleEn.contains("Colonoscopy") })
        assertTrue(instructions.any { it.titleEn.contains("Blood Glucose") })
    }

    @Test
    fun `rule based clinical simplifier extracts fasting and checklist`() {
        val rawSheet = "PATIENT PREP: Fasting for 8 hours prior to test. Do not drink milk."
        val simplified = InstructionSimplifierEngine.fallbackRuleBasedSimplification(rawSheet, "Endoscopy Test")

        assertNotNull(simplified)
        assertTrue(simplified.checklist.isNotEmpty())
        assertTrue(simplified.criticalPoints.isNotEmpty())
    }
}
