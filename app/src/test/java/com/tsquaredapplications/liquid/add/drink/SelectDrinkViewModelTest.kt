package com.tsquaredapplications.liquid.add.drink

import com.tsquaredapplications.liquid.add.drink.resources.SelectDrinkResourceWrapper
import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.entry.Entry
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockBeerDrinkTypeAndIcon
import com.tsquaredapplications.liquid.test.util.mocks.mockBeerPresetDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockPresetRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockTypeRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockUserManager
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterDrinkTypeAndIcon
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterPresetDataWrapper
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SelectDrinkViewModelTest : BaseCoroutineViewModelTest<SelectDrinkState>() {

    lateinit var viewModel: SelectDrinkViewModel
    lateinit var entryRepository: EntryRepository

    private val typeRepository = mockTypeRepository()
    private val presetRepositoryWithPresets = mockPresetRepository(withPresets = true)
    private val presetRepositoryWithoutPresets = mockPresetRepository(withPresets = false)
    private val userManager = mockUserManager()
    private val userInformation = mockUserInformation()

    private val resourceWrapper = mockk<SelectDrinkResourceWrapper> {
        every { getWarningCalculations(any()) } returns WARNING_CALCULATIONS
        every { getSuggestion(any()) } returns SUGGESTION
    }

    override fun beforeEach() {
        entryRepository = mockEntryRepository()
    }

    @Nested
    @DisplayName("Given there are presets added")
    inner class PresetsAdded {

        @BeforeEach
        fun beforeEach() {
            viewModel = SelectDrinkViewModel(
                typeRepository,
                presetRepositoryWithPresets,
                entryRepository,
                userManager,
                userInformation,
                resourceWrapper
            ).apply {
                stateLiveData.observeForever(stateObserver)
            }
        }

        @Test
        fun `when view model started with presets present then state should be set to initialized with presets and drink types`() =
            runBlocking {
                viewModel.start()

                verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                assertTrue(stateList.first() is SelectDrinkState.Initialized)
                with(stateList.first() as SelectDrinkState.Initialized) {
                    assertEquals(3, presets.size)
                    assertEquals(3, drinkTypes.size)
                }
            }

        @Nested
        @DisplayName("When preset is selected")
        inner class PresetSelected {

            @Test
            fun `when preset is not alcohol, state is preset added with no alcohol warning shown`() =
                runBlocking {
                    val waterPresetDataWrapper = mockWaterPresetDataWrapper()
                    viewModel.presetSelected(waterPresetDataWrapper)

                    val entrySlot = slot<Entry>()
                    coVerify(exactly = 1) { entryRepository.insert(capture(entrySlot)) }
                    with(entrySlot.captured) {
                        assertEquals(waterPresetDataWrapper.drinkType.drinkTypeUid, drinkTypeUid)
                        assertEquals(waterPresetDataWrapper.preset.amount, amount)
                        assertEquals(waterPresetDataWrapper.preset.presetUid, presetUid)
                    }

                    verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                    with(stateList.first() as SelectDrinkState.PresetInserted) {
                        assertFalse(showAlcoholWarning)
                        assertEquals(WARNING_CALCULATIONS, alcoholCalculations)
                        assertEquals(SUGGESTION, alcoholSuggestion)
                    }
                }

            @Test
            fun `when preset is alcohol and alcohol warning not permanently dismissed state is preset added with alcohol warning shown`() =
                runBlocking {
                    every { userManager.shouldShowAlcoholWarning() } returns true

                    val beerPresetDataWrapper = mockBeerPresetDataWrapper()
                    viewModel.presetSelected(beerPresetDataWrapper)

                    val entrySlot = slot<Entry>()
                    coVerify(exactly = 1) { entryRepository.insert(capture(entrySlot)) }
                    with(entrySlot.captured) {
                        assertEquals(beerPresetDataWrapper.drinkType.drinkTypeUid, drinkTypeUid)
                        assertEquals(beerPresetDataWrapper.preset.amount, amount)
                        assertEquals(beerPresetDataWrapper.preset.presetUid, presetUid)
                    }

                    verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                    with(stateList.first() as SelectDrinkState.PresetInserted) {
                        assertTrue(showAlcoholWarning)
                        assertEquals(WARNING_CALCULATIONS, alcoholCalculations)
                        assertEquals(SUGGESTION, alcoholSuggestion)
                    }

                }

            @Test
            fun `when preset is alcohol and alcohol warning has been permanently dismissed state is preset added with alcohol warning shown`() =
                runBlocking {
                    every { userManager.shouldShowAlcoholWarning() } returns false

                    val beerPresetDataWrapper = mockBeerPresetDataWrapper()
                    viewModel.presetSelected(beerPresetDataWrapper)

                    val entrySlot = slot<Entry>()
                    coVerify(exactly = 1) { entryRepository.insert(capture(entrySlot)) }
                    with(entrySlot.captured) {
                        assertEquals(beerPresetDataWrapper.drinkType.drinkTypeUid, drinkTypeUid)
                        assertEquals(beerPresetDataWrapper.preset.amount, amount)
                        assertEquals(beerPresetDataWrapper.preset.presetUid, presetUid)
                    }

                    verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                    with(stateList.first() as SelectDrinkState.PresetInserted) {
                        assertFalse(showAlcoholWarning)
                        assertEquals(WARNING_CALCULATIONS, alcoholCalculations)
                        assertEquals(SUGGESTION, alcoholSuggestion)
                    }

                }
        }
    }

    @Nested
    @DisplayName("Given there are not presets added")
    inner class NoPresetsAdded {

        @BeforeEach
        fun beforeEach() {
            viewModel = SelectDrinkViewModel(
                typeRepository,
                presetRepositoryWithoutPresets,
                entryRepository,
                userManager,
                userInformation,
                resourceWrapper
            ).apply {
                stateLiveData.observeForever(stateObserver)
            }
        }

        @Test
        fun `when view model started without presets present then state should be set to initialized with no presets and all drink types`() =
            runBlocking {
                viewModel.start()

                verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                assertTrue(stateList.first() is SelectDrinkState.Initialized)
                with(stateList.first() as SelectDrinkState.Initialized) {
                    assertEquals(0, presets.size)
                    assertEquals(3, drinkTypes.size)
                }
            }

        @Nested
        @DisplayName("When drink type selected")
        inner class DrinkTypeSelected {

            @Test
            fun `when drink type is not alcohol, state is drink type selected with no alcohol warning`() =
                runBlocking {
                    val waterDrinkTypeAndIcon = mockWaterDrinkTypeAndIcon()
                    viewModel.onDrinkTypeSelected(waterDrinkTypeAndIcon)

                    verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                    with(stateList.first() as SelectDrinkState.DrinkTypeSelected) {
                        assertEquals(waterDrinkTypeAndIcon, drinkTypeAndIcon)
                        assertFalse(showAlcoholWarning)
                        assertEquals(WARNING_CALCULATIONS, alcoholCalculations)
                        assertEquals(SUGGESTION, alcoholSuggestion)
                    }
                }

            @Test
            fun `when drink type is alcohol and alcohol warning not permanently dismissed, state is drink type selected with alcohol warning`() =
                runBlocking {
                    every { userManager.shouldShowAlcoholWarning() } returns true
                    val beerDrinkypeAndIcon = mockBeerDrinkTypeAndIcon()
                    viewModel.onDrinkTypeSelected(beerDrinkypeAndIcon)

                    verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                    with(stateList.first() as SelectDrinkState.DrinkTypeSelected) {
                        assertEquals(beerDrinkypeAndIcon, drinkTypeAndIcon)
                        assertTrue(showAlcoholWarning)
                        assertEquals(WARNING_CALCULATIONS, alcoholCalculations)
                        assertEquals(SUGGESTION, alcoholSuggestion)
                    }
                }

            @Test
            fun `when drink type is alcohol and alcohol warning permanently dismissed, state is drink type selected without alcohol warning`() =
                runBlocking {
                    every { userManager.shouldShowAlcoholWarning() } returns false
                    val beerDrinkypeAndIcon = mockBeerDrinkTypeAndIcon()
                    viewModel.onDrinkTypeSelected(beerDrinkypeAndIcon)

                    verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
                    with(stateList.first() as SelectDrinkState.DrinkTypeSelected) {
                        assertEquals(beerDrinkypeAndIcon, drinkTypeAndIcon)
                        assertFalse(showAlcoholWarning)
                        assertEquals(WARNING_CALCULATIONS, alcoholCalculations)
                        assertEquals(SUGGESTION, alcoholSuggestion)
                    }
                }
        }
    }

    @Nested
    @DisplayName("Given alcohol warning is dismissed")
    inner class AlcoholWarningDismissed {

        @BeforeEach
        fun beforeEach() {
            viewModel = SelectDrinkViewModel(
                typeRepository,
                presetRepositoryWithoutPresets,
                entryRepository,
                userManager,
                userInformation,
                resourceWrapper
            )
        }

        @Test
        fun `when user elects to not see warning again update set dont show warning`() {
            viewModel.alcoholWarningDismissed(dontShowAlcoholWarningAgain = true)
            verify { userManager.setDonNotShowAlcoholWarning() }
        }

        @Test
        fun `when user does not elect to not see warning again do not update set dont show warning`() {
            viewModel.alcoholWarningDismissed(dontShowAlcoholWarningAgain = false)
            verify(exactly = 0) { userManager.setDonNotShowAlcoholWarning() }
        }
    }

    companion object {
        const val WARNING_CALCULATIONS = "WARNING_CALCULATIONS"
        const val SUGGESTION = "SUGGESTION"
    }
}