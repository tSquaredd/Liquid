package com.tsquaredapplications.liquid.presets.type

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.database.types.Type
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
internal class PresetTypeSelectionViewModelTest : BaseViewModelTest() {

    private val stateObserver = mockk<Observer<PresetTypeSelectionState>>(relaxed = true)
    private val stateSlot = slot<PresetTypeSelectionState>()

    private lateinit var viewModel: PresetTypeSelectionViewModel

    private val waterTypeMock = mockk<Type> {
        every { name } returns WATER_NAME
        every { iconPath } returns WATER_ICON_NAME
        every { hydration } returns WATER_HYDRATION
    }

    private val beerTypeMock = mockk<Type> {
        every { name } returns BEER_NAME
        every { iconPath } returns BEER_ICON_NAME
        every { hydration } returns BEER_HYDRATION
    }

    private val spiritTypeMock = mockk<Type> {
        every { name } returns SPIRIT_NAME
        every { iconPath } returns SPIRIT_ICON_NAME
        every { hydration } returns SPIRIT_HYDRATION
    }

    private val typeList = listOf(waterTypeMock, beerTypeMock, spiritTypeMock)

    @BeforeEach
    fun beforeEach() {
        clearMocks(stateObserver)
        viewModel = PresetTypeSelectionViewModel(typeList)
        viewModel.stateLiveData.observeForever(stateObserver)
    }

    @Test
    fun `when view model is initialized then set initialized state`() {
        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateSlot)) }
        with(stateSlot.captured as Initialized) {
            assertEquals(beerTypeMock, typeItems[0].typeModel)
            assertEquals(spiritTypeMock, typeItems[1].typeModel)
            assertEquals(waterTypeMock, typeItems[2].typeModel)
        }
    }

    @Test
    fun `when type item clicked then set type selected state with the type selected`() {
        val waterTypeItem = mockk<TypeItem> {
            every { typeModel } returns waterTypeMock
        }
        viewModel.onItemClick(waterTypeItem)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateSlot)) }
        val typeSelectedState = stateSlot.captured as TypeSelected
        assertEquals(waterTypeMock, typeSelectedState.type)
    }

    companion object {
        const val WATER_NAME = "WATER_NAME"
        const val WATER_ICON_NAME = "WATER_ICON_NAME"
        const val WATER_HYDRATION = 1.0

        const val BEER_NAME = "BEER_NAME"
        const val BEER_ICON_NAME = "BEER_ICON_NAME"
        const val BEER_HYDRATION = -1.0

        const val SPIRIT_NAME = "SPIRIT_NAME"
        const val SPIRIT_ICON_NAME = "SPIRIT_ICON_NAME"
        const val SPIRIT_HYDRATION = -8.0

    }
}