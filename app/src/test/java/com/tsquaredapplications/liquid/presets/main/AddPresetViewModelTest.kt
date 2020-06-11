package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.presets.PresetDatabaseManager
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.login.LiquidUnit
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.AddPresetFailed
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.AddPresetSuccess
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.DrinkTypeSelected
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.Initialized
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidAmount
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidDrinkType
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidIcon
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidName
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.PresetIconSelected
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.ShowProgressBar
import com.tsquaredapplications.liquid.presets.main.resources.AddPresetResourceWrapper
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class AddPresetViewModelTest : BaseViewModelTest() {

    private val userInformation = mockk<UserInformation> {
        every { unitPreference } returns expectedUnitPreference
    }

    private val presetDatabaseManager = mockk<PresetDatabaseManager>()
    private val resourceWrapper = mockk<AddPresetResourceWrapper> {
        every { nameErrorMessage } returns NAME_ERROR_MESSAGE
        every { typeErrorMessage } returns DRINK_TYPE_ERROR_MESSAGE
        every { amountErrorMessage } returns AMOUNT_ERROR_MESSAGE
    }

    private lateinit var viewModel: AddPresetViewModel

    private var stateList = mutableListOf<AddPresetState>()
    private val stateObserver = mockk<Observer<AddPresetState>>(relaxed = true)

    @BeforeEach
    fun beforeEach() {
        clearMocks(stateObserver)
        stateList = mutableListOf()
        viewModel = AddPresetViewModel(userInformation, presetDatabaseManager, resourceWrapper)
        viewModel.stateLiveData.observeForever(stateObserver)
    }

    @Test
    fun `Given view model is started - then return initialized state with user unit preference`() {
        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        with(stateList.first() as Initialized) {
            assertEquals(expectedUnitPreference, unit)
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
        private val name = "Water Bottle"
        private val drinkType = mockk<DrinkType>()
        private val iconSelected = mockk<Icon>()
        private val amount = 20.0


        @Test
        fun `when name is empty return invalid name state`() {
            mockInteractions(
                drinkType = drinkType,
                iconSelected = iconSelected,
                amount = amount
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
                name = name,
                iconSelected = iconSelected,
                amount = amount
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
                name = name,
                drinkType = drinkType,
                amount = amount
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
                name = name,
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
        fun `when all inputs are valid and add is successful return success state`() {
            mockInteractions(
                name = name,
                drinkType = drinkType,
                iconSelected = iconSelected,
                amount = amount
            )
            val preset = mockk<Preset>()

            every { presetDatabaseManager.addPreset(any(), any(), any()) } answers {
                viewModel.onAddPresetSuccess(preset)
            }

            viewModel.onAddClicked()
            verify(exactly = 4) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                DrinkTypeSelected::class,
                PresetIconSelected::class,
                ShowProgressBar::class,
                AddPresetSuccess::class
            )
            with(stateList[3] as AddPresetSuccess) {
                assertEquals(this.preset, preset)
            }
        }

        @Test
        fun `when all inputs are valid and add fails return failed state`() {
            mockInteractions(
                name = name,
                drinkType = drinkType,
                iconSelected = iconSelected,
                amount = amount
            )
            every { presetDatabaseManager.addPreset(any(), any(), any()) } answers {
                viewModel.onAddPresetFailed()
            }

            viewModel.onAddClicked()
            verify(exactly = 4) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                DrinkTypeSelected::class,
                PresetIconSelected::class,
                ShowProgressBar::class,
                AddPresetFailed::class
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
        val expectedUnitPreference = LiquidUnit.ML

        const val NAME_ERROR_MESSAGE = "NAME_ERROR_MESSAGE"
        const val DRINK_TYPE_ERROR_MESSAGE = "DRINK_TYPE_ERROR_MESSAGE"
        const val AMOUNT_ERROR_MESSAGE = "AMOUNT_ERROR_MESSAGE"
    }

}
// TODO