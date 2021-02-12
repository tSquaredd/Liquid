package com.tsquaredapplications.liquid.home

import com.tsquaredapplications.liquid.common.BaseCoroutineViewModelTest
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState.Initialize
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState.SetProgress
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState.UpdateProgress
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.mockEntryRepository
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HomeViewModelTest : BaseCoroutineViewModelTest<HomeState>() {


    private val resourceWrapper = mockk<HomeResourceWrapper> {
        every { hydratingText } returns HYDRATING_TEXT
        every { dehydratingText } returns DEHYDRATING_TEXT
    }

    private val userInformation = mockUserInformation()

    private lateinit var viewModel: HomeViewModel
    private lateinit var entryRepository: EntryRepository

    @Nested
    @DisplayName("Start with Animation")
    inner class StartWithAnimation {
        @Test
        fun `given no entries, when started, then show all zero values`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = false, withNonAlcoholEntries = false)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = true)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()

            (stateList[1] as UpdateProgress).assert(
                currentGoalPercentage = 0,
                previousGoalPercentage = 0,
                currentHydratingAmount = 0,
                previousHydratingAmount = 0,
                currentDehydratingAmount = 0,
                previousDehydratingAmount = 0
            )
        }

        @Test
        fun `given positive entries, when started, set appropriate values`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = false, withNonAlcoholEntries = true)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = true)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()

            (stateList[1] as UpdateProgress).assert(
                currentGoalPercentage = 25,
                previousGoalPercentage = 16,
                currentHydratingAmount = 24,
                previousHydratingAmount = 16,
                currentDehydratingAmount = 0,
                previousDehydratingAmount = 0
            )
        }

        @Test
        fun `given negative entries, when started, set appropriate values`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = true, withNonAlcoholEntries = false)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = true)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()

            (stateList[1] as UpdateProgress).assert(
                currentGoalPercentage = 0,
                previousGoalPercentage = 0,
                currentHydratingAmount = 0,
                previousHydratingAmount = 0,
                currentDehydratingAmount = 12,
                previousDehydratingAmount = 0
            )
        }

        @Test
        fun `given mixed entries, when started, set appropriate values`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = true, withNonAlcoholEntries = true)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = true)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()

            (stateList[1] as UpdateProgress).assert(
                currentGoalPercentage = 12,
                previousGoalPercentage = 4,
                currentHydratingAmount = 24,
                previousHydratingAmount = 16,
                currentDehydratingAmount = 12,
                previousDehydratingAmount = 12
            )
        }
    }

    @Nested
    @DisplayName("Start without Animation")
    inner class StartWithoutAnimation {
        @Test
        fun `given no entries, when started, then show all zero values`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = false, withNonAlcoholEntries = false)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = false)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()

            (stateList[1] as SetProgress).assert(
                goalPercentage = 0,
                hydratingAmount = 0,
                dehydratingAmount = 0
            )
        }

        @Test
        fun `given positive entries, when started, then set appropriate states`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = false, withNonAlcoholEntries = true)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = false)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()
            (stateList[1] as SetProgress).assert(
                goalPercentage = 25,
                hydratingAmount = 24,
                dehydratingAmount = 0
            )
        }

        @Test
        fun `given negative entries, when started, then set percentage to zero with a dehyrating amount`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = true, withNonAlcoholEntries = false)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = false)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()
            (stateList[1] as SetProgress).assert(
                goalPercentage = 0,
                hydratingAmount = 0,
                dehydratingAmount = 12
            )
        }

        @Test
        fun `given mixed entries, when started, then set values to appropriate amounts`() {
            entryRepository =
                mockEntryRepository(withAlcoholEntries = true, withNonAlcoholEntries = true)

            viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository).apply {
                stateLiveData.observeForever(stateObserver)
                start(withAnimation = false)
            }

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            (stateList.first() as Initialize).assert()
            (stateList[1] as SetProgress).assert(
                goalPercentage = 12,
                hydratingAmount = 24,
                dehydratingAmount = 12
            )
        }
    }

    private fun Initialize.assert() {
        assertEquals(HYDRATING_TEXT, hydratingText)
        assertEquals(DEHYDRATING_TEXT, dehydratingText)
    }

    private fun UpdateProgress.assert(
        currentGoalPercentage: Int,
        previousGoalPercentage: Int,
        currentHydratingAmount: Int,
        previousHydratingAmount: Int,
        currentDehydratingAmount: Int,
        previousDehydratingAmount: Int
    ) {
        assertEquals(currentGoalPercentage, this.currentGoalPercentage)
        assertEquals(previousGoalPercentage, this.previousGoalPercentage)
        assertEquals(currentHydratingAmount, this.currentHydratingAmount)
        assertEquals(previousHydratingAmount, this.previousHydratingAmount)
        assertEquals(currentDehydratingAmount, this.currentDehydratingAmount)
        assertEquals(previousDehydratingAmount, this.previousDehydratingAmount)
    }

    private fun SetProgress.assert(
        goalPercentage: Int,
        hydratingAmount: Int,
        dehydratingAmount: Int
    ) {
        assertEquals(goalPercentage, this.goalPercentage)
        assertEquals(hydratingAmount, this.hydratingAmount)
        assertEquals(dehydratingAmount, this.dehydratingAmount)
    }

    companion object {
        private const val HYDRATING_TEXT = "HYDRATING_TEXT"
        private const val DEHYDRATING_TEXT = "DEHYDRATING_TEXT"
    }
}