package com.tsquaredapplications.liquid.presets.icon

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.IconSelected
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.icon.adapter.PresetIconItem
import com.tsquaredapplications.liquid.test.util.mocks.assertBeerIcon
import com.tsquaredapplications.liquid.test.util.mocks.assertTeaIcon
import com.tsquaredapplications.liquid.test.util.mocks.assertWaterIcon
import com.tsquaredapplications.liquid.test.util.mocks.assertWaterPresetIcon
import com.tsquaredapplications.liquid.test.util.mocks.mockIconRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterPresetIcon
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PresetIconSelectionViewModelTest :
    BaseCoroutineViewModelTest<PresetIconSelectionState>() {

    lateinit var viewModel: PresetIconSelectionViewModel

    private val iconRepository = mockIconRepository()

    @BeforeEach
    fun beforeEach() {
        viewModel = PresetIconSelectionViewModel(iconRepository).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Test
    fun `given viewModel is started set state to initialized`() {
        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is Initialized }
        with((stateList.first() as Initialized).typeItems) {
            get(0).iconModel.assertWaterIcon()
            get(1).iconModel.assertBeerIcon()
            get(2).iconModel.assertTeaIcon()
        }
    }

    @Test
    fun `given icon is selected set state to IconSelected with selected Icon model`() {
        val selectedIcon = mockWaterPresetIcon()
        val presetIconModel = mockk<PresetIconItem> {
            every { iconModel } returns selectedIcon
        }

        viewModel.onItemClick(presetIconModel)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is IconSelected }
        with(stateList.first() as IconSelected) {
            icon.assertWaterPresetIcon()
        }
    }
}