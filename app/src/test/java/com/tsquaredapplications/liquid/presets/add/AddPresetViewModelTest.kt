package com.tsquaredapplications.liquid.presets.add

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.DrinkTypeSelected
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.Initialized
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidAmount
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidDrinkType
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidIcon
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidName
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.PresetIconSelected
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_NAME
import com.tsquaredapplications.liquid.test.util.mocks.mockPresetRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterDrinkType
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterPresetIcon
import io.mockk.clearMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AddPresetViewModelTest : BaseCoroutineViewModelTest<AddPresetState>() {

    private lateinit var viewModel: AddPresetViewModel

    private val userInformation = mockUserInformation()
    private val presetRepository = mockPresetRepository(true)

    private val resourceWrapper = mockk<AddPresetResourceWrapper> {
        every { nameErrorMessage } returns NAME_ERROR_MESSAGE
        every { typeErrorMessage } returns DRINK_TYPE_ERROR_MESSAGE
        every { amountErrorMessage } returns AMOUNT_ERROR_MESSAGE
    }

    override fun beforeEach() {
        clearMocks(stateObserver)
        stateList = mutableListOf()
        viewModel =
            AddPresetViewModel(
                userInformation,
                presetRepository,
                resourceWrapper
            )
        viewModel.stateLiveData.observeForever(stateObserver)
    }

    @Test
    fun `Given view model is started - then return initialized state with user unit preference`() {
        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        with(stateList.first() as Initialized) {
            assertEquals(MOCK_UNIT_PREF, unit)
        }
    }

    @Test
    fun `Given drink type is selected - then return type selected state`() {
        val selectedType = mockk<DrinkType>()

        viewModel.drinkTypeSelected(selectedType)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        with(stateList.first() as DrinkTypeSelected) {
            assertEquals(selectedType, drinkType)
        }
    }

    @Test
    fun `Given icon is selected - then return icon selected state`() {
        val selectedIcon = mockk<Icon>()

        viewModel.presetIconSelected(selectedIcon)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        with(stateList.first() as PresetIconSelected) {
            assertEquals(selectedIcon, icon)
        }
    }

    @Nested
    @DisplayName("Given Add Preset is clicked")
    inner class OnAddPresetClicked {
        private val drinkType = mockWaterDrinkType()
        private val iconSelected = mockWaterPresetIcon()


        @Test
        fun `when name is empty return invalid name state`() {
            mockInteractions(
                drinkType = drinkType,
                iconSelected = iconSelected,
                amount = MOCK_WATER_PRESET_AMOUNT
            )

            viewModel.onAddClicked()

            verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                DrinkTypeSelected::class,
                PresetIconSelected::class,
                InvalidName::class
            )

            with(stateList[2] as InvalidName) {
                assertEquals(NAME_ERROR_MESSAGE, errorMessage)
            }
        }

        @Test
        fun `when drink type is not selected return invalid type state`() {
            mockInteractions(
                name = MOCK_WATER_PRESET_NAME,
                iconSelected = iconSelected,
                amount = MOCK_WATER_PRESET_AMOUNT
            )

            viewModel.onAddClicked()

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                PresetIconSelected::class,
                InvalidDrinkType::class
            )

            with(stateList[1] as InvalidDrinkType) {
                assertEquals(DRINK_TYPE_ERROR_MESSAGE, errorMessage)
            }
        }

        @Test
        fun `when icon is not selected return invalid icon state`() {
            mockInteractions(
                name = MOCK_WATER_PRESET_NAME,
                drinkType = drinkType,
                amount = MOCK_WATER_PRESET_AMOUNT
            )

            viewModel.onAddClicked()

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                DrinkTypeSelected::class,
                InvalidIcon::class
            )
        }

        @Test
        fun `when amount is not selected return invalid amount state`() {
            mockInteractions(
                name = MOCK_WATER_PRESET_NAME,
                drinkType = drinkType,
                iconSelected = iconSelected
            )

            viewModel.onAddClicked()

            verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                DrinkTypeSelected::class,
                PresetIconSelected::class,
                InvalidAmount::class
            )

            with(stateList[2] as InvalidAmount) {
                assertEquals(AMOUNT_ERROR_MESSAGE, errorMessage)
            }
        }

        @Test
        fun `when nothing has been selected return all invalid states`() {
            viewModel.onAddClicked()

            verify(exactly = 4) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                InvalidName::class,
                InvalidDrinkType::class,
                InvalidIcon::class,
                InvalidAmount::class
            )
        }

        @Test
        fun `when all validations pass insert preset and set state to PresetAdded`() {
            mockInteractions(
                name = MOCK_WATER_PRESET_NAME,
                amount = MOCK_WATER_PRESET_AMOUNT,
                drinkType = drinkType,
                iconSelected = iconSelected
            )

            viewModel.onAddClicked()

            val presetSlot = slot<Preset>()
            coVerify(exactly = 1) { presetRepository.insert(capture(presetSlot)) }
            with(presetSlot.captured) {
                assertEquals(MOCK_WATER_PRESET_AMOUNT, amount)
                assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
                assertEquals(MOCK_WATER_PRESET_ICON_UID, iconUid)
                assertEquals(MOCK_WATER_PRESET_NAME, name)
            }

            verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                DrinkTypeSelected::class,
                PresetIconSelected::class,
                AddPresetState.PresetAdded::class
            )
        }

        private fun mockInteractions(
            name: String? = null,
            amount: Double? = null,
            drinkType: DrinkType? = null,
            iconSelected: Icon? = null
        ) {
            with(viewModel) {
                name?.let { onNameChanged(it) }
                amount?.let { onAmountChanged(it.toString()) }
                drinkType?.let { drinkTypeSelected(it) }
                iconSelected?.let { presetIconSelected(it) }
            }
        }
    }

    companion object {
        const val NAME_ERROR_MESSAGE = "NAME_ERROR_MESSAGE"
        const val DRINK_TYPE_ERROR_MESSAGE = "DRINK_TYPE_ERROR_MESSAGE"
        const val AMOUNT_ERROR_MESSAGE = "AMOUNT_ERROR_MESSAGE"
    }
}