package com.example.myapplication

import org.junit.Test
import org.junit.Assert.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.viewmodel.SettingsViewModel
import org.junit.Rule

class SettingsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `toggleTextSize flips value`() {
        val viewModel = SettingsViewModel()

        assertEquals(false, viewModel.isTextSizeIncreased.value)
        viewModel.toggleTextSize()
        assertEquals(true, viewModel.isTextSizeIncreased.value)

    }

    @Test
    fun `initial value of isTextSizeIncreased is false`() {
        val viewModel = SettingsViewModel()

        assertEquals(false, viewModel.isTextSizeIncreased.value)
    }

    @Test
    fun `multiple toggles produce correct alternating values`() {
        val viewModel = SettingsViewModel()

        val expectedValues = listOf(true, false, true, false, true)
        val actualValues = mutableListOf<Boolean>()

        for (i in expectedValues.indices) {
            viewModel.toggleTextSize()
            actualValues.add(viewModel.isTextSizeIncreased.value ?: false)
        }

        assertEquals(expectedValues, actualValues)
    }
}