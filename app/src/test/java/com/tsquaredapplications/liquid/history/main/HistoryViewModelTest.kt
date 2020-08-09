package com.tsquaredapplications.liquid.history.main

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.history.main.resources.HistoryResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.BEER_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_DAILY_GOAL
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.TEA_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.WATER_ENTRY_AMOUNT
import com.tsquaredapplications.liquid.test.util.mocks.assertBeerEntryDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.assertTeaEntryDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.assertWaterEntryDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockBeerEntryDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockGoalRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockTeaEntryDataWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockWaterEntryDataWrapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

internal class HistoryViewModelTest : BaseCoroutineViewModelTest<HistoryState>() {

    lateinit var viewModel: HistoryViewModel
    private val goalRepository = mockGoalRepository()
    private val userInformation = mockUserInformation()
    private val resourceWrapper = mockk<HistoryResourceWrapper> {
        every { getDayDisplayName(any()) } returns DAY_DISPLAY_NAME
    }

    @Nested
    @DisplayName("Given no entries for day")
    inner class NoEntriesForDay {

        @BeforeEach
        fun beforeEach() {
            val entryRepository = mockEntryRepository(withEntries = false)
            viewModel =
                HistoryViewModel(
                    entryRepository,
                    goalRepository,
                    userInformation,
                    resourceWrapper
                ).apply {
                    stateLiveData.observeForever(stateObserver)
                }
        }

        @Test
        fun `given viewModel is started set state to init with no entries`() {
            viewModel.start()

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is HistoryState.Initialized)
            with(stateList.first() as HistoryState.Initialized) {
                assertTrue(historyItems.isEmpty())
            }
        }

    }

    @Nested
    @DisplayName("Given entries for day")
    inner class EntriesForDay {

        @Test
        fun `when entries all on same day, set state to Initialized with one day card`() {
            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 1)
            }
            val beerTimestamp = cal.timeInMillis
            val waterTimestamp = cal.apply {
                add(Calendar.HOUR_OF_DAY, 2)
            }.timeInMillis
            val teaTimestamp = cal.apply {
                add(Calendar.HOUR_OF_DAY, 2)
            }.timeInMillis

            val entries = mutableListOf<EntryDataWrapper>().apply {
                add(mockBeerEntryDataWrapper(customTimestamp = beerTimestamp))
                add(mockWaterEntryDataWrapper(customTimestamp = waterTimestamp))
                add(mockTeaEntryDataWrapper(customTimestamp = teaTimestamp))
            }

            val entryRepository = mockEntryRepository(allEntriesList = entries)

            viewModel = HistoryViewModel(
                entryRepository,
                goalRepository,
                userInformation,
                resourceWrapper
            ).apply {
                stateLiveData.observeForever(stateObserver)
                start()
            }

            verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
            assertTrue(stateList.first() is HistoryState.Initialized)
            with(stateList.first() as HistoryState.Initialized) {
                with(historyItems) {
                    assertEquals(1, size)
                    with(get(0)) {
                        assertEquals(DAY_DISPLAY_NAME, date)
                        with(entries) {
                            assertEquals(3, size)
                            get(0).assertBeerEntryDataWrapper(customTimestamp = beerTimestamp)
                            get(1).assertWaterEntryDataWrapper(customTimestamp = waterTimestamp)
                            get(2).assertTeaEntryDataWrapper(customTimestamp = teaTimestamp)
                        }

                        val expectedProgressString =
                            "${(WATER_ENTRY_AMOUNT + BEER_ENTRY_AMOUNT + TEA_ENTRY_AMOUNT).toInt()} oz / $MOCK_DAILY_GOAL oz"
                        assertEquals(expectedProgressString, progress)
                        assertEquals(MOCK_UNIT_PREF, liquidUnit)
                    }
                }
            }
        }
    }

    companion object {
        const val DAY_DISPLAY_NAME = "DAY_DISPLAY_NAME"
    }
}