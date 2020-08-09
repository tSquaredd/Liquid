package com.tsquaredapplications.liquid.presets.main

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.presets.main.PresetState.ShowPlaceholder
import com.tsquaredapplications.liquid.presets.main.PresetState.ShowPresets
import com.tsquaredapplications.liquid.test.util.mocks.assertBeerPresetDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.assertTeaPresetDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.assertWaterPresetDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockPresetRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

internal class PresetsViewModelTest : BaseCoroutineViewModelTest<PresetState>() {

    lateinit var viewModel: PresetsViewModel
    lateinit var presetRepository: PresetRepository

    private val userInformation = mockUserInformation()

    @Nested
    @DisplayName("Given no presets have been added")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class NoPresets {

        @BeforeAll
        fun beforeAll() {
            presetRepository = mockPresetRepository(withPresets = false)
        }

        @BeforeEach
        fun beforeEach() {
            viewModel = PresetsViewModel(presetRepository, userInformation).apply {
                stateLiveData.observeForever(stateObserver)
            }
        }

        @Test
        fun `when viewModel started set state to ShowPlaceholder`() {
            viewModel.start()

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue { stateList.first() is ShowPlaceholder }
        }
    }

    @Nested
    @DisplayName("Given presets have been added")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class WithPresets {

        @BeforeAll
        fun beforeAll() {
            presetRepository = mockPresetRepository(withPresets = true)
        }

        @BeforeEach
        fun beforeEach() {
            viewModel = PresetsViewModel(presetRepository, userInformation).apply {
                stateLiveData.observeForever(stateObserver)
            }
        }

        @Test
        fun `when viewModel started set state to ShowPresets`() {
            viewModel.start()

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue { stateList.first() is ShowPresets }
            with(stateList.first() as ShowPresets) {
                detailedPresets[0].presetDataWrapper.assertWaterPresetDataWrapper()
                detailedPresets[1].presetDataWrapper.assertBeerPresetDataWrapper()
                detailedPresets[2].presetDataWrapper.assertTeaPresetDataWrapper()
            }
        }
    }
}