package com.tsquaredapplications.liquid.history.edit

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.history.edit.resources.UpdateEntryResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_NAME
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_PRESET_NAME
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterEntryDataWrapper
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class UpdateEntryViewModelTest : BaseCoroutineViewModelTest<UpdateEntryState>() {

    lateinit var viewModel: UpdateEntryViewModel
    private val entryRepository = mockEntryRepository()
    private val userInformation = mockUserInformation()
    private val resourceWrapper = mockk<UpdateEntryResourceWrapper> {

    }

    @BeforeEach
    fun beforeEach() {
        clearMocks(stateObserver)
        stateList.clear()

        viewModel = UpdateEntryViewModel(entryRepository, userInformation, resourceWrapper).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }


    @Nested
    @DisplayName("Given entry is a preset")
    inner class PresetEntry {

        @Test
        fun `given viewModel is started then set state to Initialized`() {
            val waterEntryDataWrapper = mockWaterEntryDataWrapper(asPreset = true)

            viewModel.start(waterEntryDataWrapper)

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is UpdateEntryState.Initialized)
            with(stateList.first() as UpdateEntryState.Initialized) {
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
    }

    @Nested
    @DisplayName("Given entry is not a preset")
    inner class NonPresetEntry {

        @Test
        fun `given viewModel is started then set state to Initialized`() {
            val waterEntryDataWrapper = mockWaterEntryDataWrapper(asPreset = false)

            viewModel.start(waterEntryDataWrapper)

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is UpdateEntryState.Initialized)
            with(stateList.first() as UpdateEntryState.Initialized) {
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
    }

}