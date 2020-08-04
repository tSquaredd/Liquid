package com.tsquaredapplications.liquid.presets.type

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.TypeSelected
import com.tsquaredapplications.liquid.test.util.mocks.assertBeerDrinkTypeAndIcon
import com.tsquaredapplications.liquid.test.util.mocks.assertTeaDrinkTypeAndIcon
import com.tsquaredapplications.liquid.test.util.mocks.assertWaterDrinkTypeAndIcon
import com.tsquaredapplications.liquid.test.util.mocks.mockTypeRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterDrinkTypeAndIcon
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PresetTypeSelectionViewModelTest :
    BaseCoroutineViewModelTest<PresetTypeSelectionState>() {

    lateinit var viewModel: PresetTypeSelectionViewModel
    private val typeRepository = mockTypeRepository()

    @BeforeEach
    fun beforeEach() {
        viewModel = PresetTypeSelectionViewModel(typeRepository).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Test
    fun `when viewModel started then set state to Initialized`() {
        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is Initialized }
        with((stateList.first() as Initialized).typeItems) {
            get(0).drinkTypeAndIcon.assertWaterDrinkTypeAndIcon()
            get(1).drinkTypeAndIcon.assertBeerDrinkTypeAndIcon()
            get(2).drinkTypeAndIcon.assertTeaDrinkTypeAndIcon()
        }
    }

    @Test
    fun `when type selected set state to TypeSelected`() {
        val selectedType = TypeItem(mockWaterDrinkTypeAndIcon())
        viewModel.onItemClick(selectedType)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is TypeSelected }
    }
}