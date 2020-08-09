package com.tsquaredapplications.liquid.history.edit

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.entry.Entry
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.Initialized
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.InvalidAmount
import com.tsquaredapplications.liquid.history.edit.resources.UpdateEntryResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_NAME
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_NAME
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_UID
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_TIMESTAMP
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_UID
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterDrinkType
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterEntryDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterIcon
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterPreset
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class UpdateEntryViewModelTest : BaseCoroutineViewModelTest<UpdateEntryState>() {

    lateinit var viewModel: UpdateEntryViewModel
    lateinit var entryRepository: EntryRepository
    private val userInformation = mockUserInformation()
    private val resourceWrapper = mockk<UpdateEntryResourceWrapper> {
        every { invalidAmountErrorMessage } returns INVALID_AMOUNT_ERROR_MESSAGE
    }

    @BeforeEach
    fun beforeEach() {
        entryRepository = mockEntryRepository()

        viewModel = UpdateEntryViewModel(entryRepository, userInformation, resourceWrapper).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Nested
    @DisplayName("Given entry is a preset")
    inner class PresetEntry {

        private val waterEntryDataWrapper = mockWaterEntryDataWrapper(asPreset = true)

        @Test
        fun `when viewModel is started then set state to Initialized`() {
            viewModel.start(waterEntryDataWrapper)

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is Initialized)
            with(stateList.first() as Initialized) {
                assertEquals(MOCK_WATER_PRESET_NAME, name)
                assertEquals(MOCK_WATER_PRESET_AMOUNT.toString(), amount)
                with(icon) {
                    assertEquals(MOCK_WATER_PRESET_ICON_UID, iconUid)
                    assertEquals(MOCK_WATER_PRESET_ICON_RESOURCE, iconResource)
                    assertEquals(MOCK_WATER_PRESET_LARGE_ICON_RESOURCE, largeIconResource)
                }
                assertEquals(MOCK_UNIT_PREF.toString(), liquidUnit)
            }
        }

        @Test
        fun `when delete is clicked then delete entry and set state to EntryDeleted`() {
            with(viewModel) {
                start(waterEntryDataWrapper)
                onDeleteClicked()
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                Initialized::class,
                UpdateEntryState.EntryDeleted::class
            )
        }

        @Test
        fun `when amount is updated to valid amount and update is clicked save entry and set state to EntryUpdated`() {
            val realEntryDataWrapper = EntryDataWrapper(
                Entry(
                    WATER_ENTRY_UID,
                    WATER_ENTRY_AMOUNT,
                    WATER_ENTRY_TIMESTAMP,
                    MOCK_WATER_DRINK_TYPE_UID,
                    MOCK_WATER_PRESET_UID
                ),
                mockWaterDrinkType(),
                mockWaterPreset(),
                mockWaterIcon()
            )

            with(viewModel) {
                start(realEntryDataWrapper)
                onAmountChanged("42.0")
                onUpdateClicked()
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                Initialized::class,
                UpdateEntryState.EntryUpdated::class
            )

            val entrySlot = slot<Entry>()
            coVerify(exactly = 1) { entryRepository.update(capture(entrySlot)) }

            with(entrySlot.captured) {
                assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
                assertEquals(WATER_ENTRY_TIMESTAMP, timestamp)
                assertEquals(42.0, amount)
                assertEquals(MOCK_WATER_PRESET_UID, presetUid)
            }
        }
    }

    @Nested
    @DisplayName("Given entry is not a preset")
    inner class NonPresetEntry {

        private val waterEntryDataWrapper = mockWaterEntryDataWrapper(asPreset = false)

        @Test
        fun `when viewModel is started then set state to Initialized`() {
            viewModel.start(waterEntryDataWrapper)

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is Initialized)
            with(stateList.first() as Initialized) {
                assertEquals(MOCK_WATER_DRINK_TYPE_NAME, name)
                assertEquals(WATER_ENTRY_AMOUNT.toString(), amount)
                with(icon) {
                    assertEquals(MOCK_WATER_ICON_UID, iconUid)
                    assertEquals(MOCK_WATER_ICON_RESOURCE, iconResource)
                    assertEquals(MOCK_WATER_LARGE_ICON_RESOURCE, largeIconResource)
                }
                assertEquals(MOCK_UNIT_PREF.toString(), liquidUnit)
            }
        }

        @Test
        fun `when delete is clicked then delete entry and set state to EntryDeleted`() {
            with(viewModel) {
                start(waterEntryDataWrapper)
                onDeleteClicked()
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                Initialized::class,
                UpdateEntryState.EntryDeleted::class
            )
        }

        @Test
        fun `when amount is updated to valid amount and update is clicked save entry and set state to EntryUpdated`() {
            val realEntryDataWrapper = EntryDataWrapper(
                Entry(
                    WATER_ENTRY_UID,
                    WATER_ENTRY_AMOUNT,
                    WATER_ENTRY_TIMESTAMP,
                    MOCK_WATER_DRINK_TYPE_UID,
                    null
                ),
                mockWaterDrinkType(),
                null,
                mockWaterIcon()
            )

            with(viewModel) {
                start(realEntryDataWrapper)
                onAmountChanged("42.0")
                onUpdateClicked()
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                Initialized::class,
                UpdateEntryState.EntryUpdated::class
            )

            val entrySlot = slot<Entry>()
            coVerify(exactly = 1) { entryRepository.update(capture(entrySlot)) }

            with(entrySlot.captured) {
                assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
                assertEquals(WATER_ENTRY_TIMESTAMP, timestamp)
                assertEquals(42.0, amount)
                assertNull(presetUid)
            }
        }
    }

    @Test
    fun `when amount is updated to invalid amount and update is clicked set state to InvalidAmount`() {
        with(viewModel) {
            start(mockWaterEntryDataWrapper())
            onAmountChanged("")
            onUpdateClicked()
        }

        verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
        stateList.assertStateOrder(
            Initialized::class,
            InvalidAmount::class
        )

        assertEquals(INVALID_AMOUNT_ERROR_MESSAGE, (stateList[1] as InvalidAmount).errorMessage)
    }

    companion object {
        const val INVALID_AMOUNT_ERROR_MESSAGE = "INVALID_AMOUNT_ERROR_MESSAGE"
    }
}