package com.tsquaredapplications.liquid.presets.type

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.TypeSelected
import com.tsquaredapplications.liquid.presets.type.adapter.TypeItem
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PresetDrinkTypeSelectionViewModelTest : BaseViewModelTest() {

    private val stateObserver = mockk<Observer<PresetTypeSelectionState>>(relaxed = true)
    private val stateSlot = slot<PresetTypeSelectionState>()

    private lateinit var viewModel: PresetTypeSelectionViewModel

    private val waterTypeMock = mockk<DrinkType> {
        every { name } returns WATER_NAME
//        every { icon } returns mockk {
//            every { iconPath } returns WATER_ICON_PATH
//            every { largeIconPath } returns WATER_LARGE_ICON_PATH
//        }
        every { hydration } returns WATER_HYDRATION
    }

    private val beerTypeMock = mockk<DrinkType> {
        every { name } returns BEER_NAME
//        every { icon } returns mockk {
//            every { iconPath } returns BEER_ICON_PATH
//            every { largeIconPath } returns BEER_LARGE_ICON_PATH
//        }
        every { hydration } returns BEER_HYDRATION
    }

    private val spiritTypeMock = mockk<DrinkType> {
        every { name } returns SPIRIT_NAME
//        every { icon } returns mockk {
//            every { iconPath } returns SPIRIT_ICON_PATH
//            every { largeIconPath } returns SPIRIT_LARGE_ICON_PATH
//        }
        every { hydration } returns SPIRIT_HYDRATION
    }

    private val typeList = listOf(waterTypeMock, beerTypeMock, spiritTypeMock)

    @BeforeEach
    fun beforeEach() {
        clearMocks(stateObserver)
//        viewModel = PresetTypeSelectionViewModel(typeList)
        viewModel.stateLiveData.observeForever(stateObserver)
    }

    @Test
    fun `when view model is initialized then set initialized state`() {
//        viewModel.start()
//
//        verify(exactly = 1) { stateObserver.onChanged(capture(stateSlot)) }
//        with(stateSlot.captured as Initialized) {
//            assertEquals(beerTypeMock, typeItems[0].drinkType)
//            assertEquals(spiritTypeMock, typeItems[1].drinkType)
//            assertEquals(waterTypeMock, typeItems[2].drinkType)
//        }
    }

    @Test
    fun `when type item clicked then set type selected state with the type selected`() {
        val waterTypeItem = mockk<TypeItem> {
//            every { drinkType } returns waterTypeMock
        }
        viewModel.onItemClick(waterTypeItem)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateSlot)) }
        val typeSelectedState = stateSlot.captured as TypeSelected
        assertEquals(waterTypeMock, typeSelectedState.drinkType)
    }

    companion object {
        const val WATER_NAME = "WATER_NAME"
        const val WATER_ICON_PATH = "WATER_ICON_PATH"
        const val WATER_LARGE_ICON_PATH = "WATER_LARGE_ICON_PATH"
        const val WATER_HYDRATION = 1.0

        const val BEER_NAME = "BEER_NAME"
        const val BEER_ICON_PATH = "BEER_ICON_PATH"
        const val BEER_LARGE_ICON_PATH = "BEER_LARGE_ICON_PATH"
        const val BEER_HYDRATION = -1.0

        const val SPIRIT_NAME = "SPIRIT_NAME"
        const val SPIRIT_ICON_PATH = "SPIRIT_ICON_PATH"
        const val SPIRIT_LARGE_ICON_PATH = "SPIRIT_LARGE_ICON_PATH"
        const val SPIRIT_HYDRATION = -8.0
    }
}