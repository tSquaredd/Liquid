package com.tsquaredapplications.liquid.home

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.home.model.HomeState
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HomeViewModelTest : BaseCoroutineViewModelTest<HomeState>() {


    private val resourceWrapper = mockk<HomeResourceWrapper> {
        every { getGoalProgressText(any(), any(), any()) } returns GOAL_PROGRESS_TEXT
    }

    private val userInformation = mockk<UserInformation> {
        every { weight } returns WEIGHT
        every { dailyGoal } returns GOAL
        every { unitPreference } returns UNIT
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var entryRepository: EntryRepository

    @Test
    fun `when viewModel is started with no entries set state to Initialized with not isNegative`() {
        entryRepository =
            mockEntryRepository(withAlcoholEntries = false, withNonAlcoholEntries = false)

        viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
            stateLiveData.observeForever(stateObserver)
            start()
        }

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        with(stateList.first() as HomeState.Initialized) {
            assertEquals(GOAL_PROGRESS_TEXT, goalProgress)
            assertFalse(isNegative)
        }
    }

    @Test
    fun `when viewModel is started with non alcohol entries set state to Initialized with not isNegative`() {
        entryRepository =
            mockEntryRepository(withAlcoholEntries = false, withNonAlcoholEntries = true)

        viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
            stateLiveData.observeForever(stateObserver)
            start()
        }

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        with(stateList.first() as HomeState.Initialized) {
            assertEquals(GOAL_PROGRESS_TEXT, goalProgress)
            assertFalse(isNegative)
        }
    }

    @Test
    fun `when viewModel is started with only alcohol entries set state to Initialized with not isNegative`() {
        entryRepository =
            mockEntryRepository(withAlcoholEntries = true, withNonAlcoholEntries = false)

        viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
            stateLiveData.observeForever(stateObserver)
            start()
        }

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        with(stateList.first() as HomeState.Initialized) {
            assertEquals(GOAL_PROGRESS_TEXT, goalProgress)
            assertTrue(isNegative)
        }
    }

    companion object {
        private const val GOAL_PROGRESS_TEXT = "GOAL_PROGRESS_TEXT"
        private const val WEIGHT = 200
        private const val GOAL = 100
        private val UNIT = LiquidUnit.OZ
    }
}