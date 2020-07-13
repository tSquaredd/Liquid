package com.tsquaredapplications.liquid.history.day

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.test.util.mocks.BEER_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.BEER_ENTRY_TIMESTAMP
import com.tsquaredapplications.liquid.test.util.mocks.BEER_ENTRY_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_BEER_DRINK_TYPE_HYDRATION
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_BEER_DRINK_TYPE_IS_ALCOHOL
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_BEER_DRINK_TYPE_NAME
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_BEER_DRINK_TYPE_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_BEER_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_BEER_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_BEER_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_TEA_DRINK_TYPE_HYDRATION
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_TEA_DRINK_TYPE_IS_ALCOHOL
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_TEA_DRINK_TYPE_NAME
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_TEA_DRINK_TYPE_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_TEA_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_TEA_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_TEA_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_HYDRATION
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_IS_ALCOHOL
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_NAME
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_DRINK_TYPE_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_ICON_UID
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_WATER_LARGE_ICON_RESOURCE
import com.tsquaredapplications.liquid.test.util.mocks.TEA_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.TEA_ENTRY_TIMESTAMP
import com.tsquaredapplications.liquid.test.util.mocks.TEA_ENTRY_UID
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_TIMESTAMP
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_UID
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DayHistoryViewModelTest : BaseCoroutineViewModelTest<DayHistoryState>() {

    lateinit var viewModel: DayHistoryViewModel

    private val userInformation = mockUserInformation()
    private val timestampRange = mockk<TimestampRange> {
        every { startTime } returns 0
        every { endTime } returns 1
    }
    private val resourceWrapper = mockk<DayHistoryResourceWrapper> {
        every { getScreenTitle(any()) } returns SCREEN_TITLE
    }

    @BeforeEach
    fun beforeEach() {
        clearMocks(stateObserver)
        stateList.clear()
    }

    @Nested
    @DisplayName("Given there are no entries for the day")
    inner class NoEntriesForDay {

        @BeforeEach
        fun beforeEach() {
            viewModel = DayHistoryViewModel(
                mockEntryRepository(withEntriesForTimeRange = false),
                userInformation,
                resourceWrapper
            ).apply { stateLiveData.observeForever(stateObserver) }
        }

        @Test
        fun `when viewModel is started set state to NoEntriesForDay`() = runBlocking {
            viewModel.start(timestampRange)

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is DayHistoryState.NoEntriesForDay)
        }
    }

    @Nested
    @DisplayName("Given there are entries for the day")
    inner class DayWithEntries {
        @BeforeEach
        fun beforeEach() {
            viewModel = DayHistoryViewModel(
                mockEntryRepository(withEntriesForTimeRange = true),
                userInformation,
                resourceWrapper
            ).apply { stateLiveData.observeForever(stateObserver) }
        }

        @Test
        fun `when viewModel is started set state to Initialized`() = runBlocking {
            viewModel.start(timestampRange)

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is DayHistoryState.Initialized)
            with(stateList.first() as DayHistoryState.Initialized) {
                assertEquals(3, historyIconModels.size)
                with(historyIconModels[0].model) {
                    assertTrue(detailed)
                    assertEquals(MOCK_UNIT_PREF, liquidUnit)
                    with(entryDataWrapper) {
                        with(entry) {
                            assertEquals(WATER_ENTRY_UID, entryUid)
                            assertEquals(WATER_ENTRY_AMOUNT, amount)
                            assertEquals(WATER_ENTRY_TIMESTAMP, timestamp)
                            assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
                        }
                        with(drinkType) {
                            assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
                            assertEquals(MOCK_WATER_DRINK_TYPE_HYDRATION, hydration)
                            assertEquals(MOCK_WATER_DRINK_TYPE_IS_ALCOHOL, isAlcohol)
                            assertEquals(MOCK_WATER_DRINK_TYPE_NAME, name)
                        }
                        with(icon) {
                            assertEquals(MOCK_WATER_ICON_UID, iconUid)
                            assertEquals(MOCK_WATER_ICON_RESOURCE, iconResource)
                            assertEquals(MOCK_WATER_LARGE_ICON_RESOURCE, largeIconResource)
                        }
                    }
                }

                with(historyIconModels[1].model) {
                    assertTrue(detailed)
                    assertEquals(MOCK_UNIT_PREF, liquidUnit)
                    with(entryDataWrapper) {
                        with(entry) {
                            assertEquals(BEER_ENTRY_UID, entryUid)
                            assertEquals(BEER_ENTRY_AMOUNT, amount)
                            assertEquals(BEER_ENTRY_TIMESTAMP, timestamp)
                            assertEquals(MOCK_BEER_DRINK_TYPE_UID, drinkTypeUid)
                        }
                        with(drinkType) {
                            assertEquals(MOCK_BEER_DRINK_TYPE_UID, drinkTypeUid)
                            assertEquals(MOCK_BEER_DRINK_TYPE_HYDRATION, hydration)
                            assertEquals(MOCK_BEER_DRINK_TYPE_IS_ALCOHOL, isAlcohol)
                            assertEquals(MOCK_BEER_DRINK_TYPE_NAME, name)
                        }
                        with(icon) {
                            assertEquals(MOCK_BEER_ICON_UID, iconUid)
                            assertEquals(MOCK_BEER_ICON_RESOURCE, iconResource)
                            assertEquals(MOCK_BEER_LARGE_ICON_RESOURCE, largeIconResource)
                        }
                    }
                }

                with(historyIconModels[2].model) {
                    assertTrue(detailed)
                    assertEquals(MOCK_UNIT_PREF, liquidUnit)
                    with(entryDataWrapper) {
                        with(entry) {
                            assertEquals(TEA_ENTRY_UID, entryUid)
                            assertEquals(TEA_ENTRY_AMOUNT, amount)
                            assertEquals(TEA_ENTRY_TIMESTAMP, timestamp)
                            assertEquals(MOCK_TEA_DRINK_TYPE_UID, drinkTypeUid)
                        }
                        with(drinkType) {
                            assertEquals(MOCK_TEA_DRINK_TYPE_UID, drinkTypeUid)
                            assertEquals(MOCK_TEA_DRINK_TYPE_HYDRATION, hydration)
                            assertEquals(MOCK_TEA_DRINK_TYPE_IS_ALCOHOL, isAlcohol)
                            assertEquals(MOCK_TEA_DRINK_TYPE_NAME, name)
                        }
                        with(icon) {
                            assertEquals(MOCK_TEA_ICON_UID, iconUid)
                            assertEquals(MOCK_TEA_ICON_RESOURCE, iconResource)
                            assertEquals(MOCK_TEA_LARGE_ICON_RESOURCE, largeIconResource)
                        }
                    }
                }
            }

        }
    }

    companion object {
        private const val SCREEN_TITLE = "SCREEN_TITLE"
    }

}