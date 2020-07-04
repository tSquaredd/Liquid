package com.tsquaredapplications.liquid.history.main

import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.goal.Goal
import com.tsquaredapplications.liquid.common.database.goal.GoalRepository
import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.history.main.resources.HistoryResourceWrapper
import com.tsquaredapplications.liquid.setup.LiquidUnit
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class HistoryViewModelTest : BaseViewModelTest<HistoryState>() {

    private val entryRepository = mockk<EntryRepository>()
    private val goalRepository = mockk<GoalRepository>()
    private val userInformation = mockk<UserInformation> {
        every { unitPreference } returns LiquidUnit.OZ
        every { dailyGoal } returns 90
    }

    private val iconRepository = mockk<IconRepository>()

    private val historyResourceWrapper = mockk<HistoryResourceWrapper> {
        every { getDayDisplayName(any()) } returns "Day Name"
    }

    lateinit var viewModel: HistoryViewModel

    @BeforeEach
    fun beforeEach() {
        viewModel = HistoryViewModel(
            entryRepository,
            goalRepository,
            userInformation,
            iconRepository,
            historyResourceWrapper
        )
    }

    @Test
    fun `history with no entries`() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val result = viewModel.buildHistoryDayItems(
            mutableListOf(),
            mutableListOf(Goal(1, 80, calendar.timeInMillis)),
            mapOf()
        )
        assertTrue(result.isEmpty())
    }

    @Test
    fun `history with one day of entries`() {
        val calendar = Calendar.getInstance()
        val entryOne = mockk<EntryDataWrapper> {
            every { entry.amount } returns 10.0
            every { entry.timestamp } returns calendar.timeInMillis - 10000
            every { preset } returns null
            every { drinkType } returns DrinkType(1, "Water", 1.0, false, 1)
        }

        val entryTwo = mockk<EntryDataWrapper> {
            every { entry.amount } returns 15.0
            every { entry.timestamp } returns calendar.timeInMillis
            every { preset } returns null
            every { drinkType } returns DrinkType(1, "Water", 1.0, false, 1)
        }
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val result = viewModel.buildHistoryDayItems(
            mutableListOf(entryOne, entryTwo),
            mutableListOf(Goal(1, 80, calendar.timeInMillis)),
            mapOf()
        )
        assertFalse(result.isEmpty())
        assertTrue(result.size == 1)
    }

    @Test
    fun `history with two days of entries`() {
        val calendar = Calendar.getInstance()
        val entryOne = mockk<EntryDataWrapper> {
            every { entry.amount } returns 10.0
            every { entry.timestamp } returns calendar.timeInMillis - 10000
            every { preset } returns null
            every { drinkType } returns DrinkType(1, "Water", 1.0, false, 1)
        }

        val entryTwo = mockk<EntryDataWrapper> {
            every { entry.amount } returns 15.0
            every { entry.timestamp } returns calendar.timeInMillis
            every { preset } returns null
            every { drinkType } returns DrinkType(1, "Water", 1.0, false, 1)
        }

        val entryThree = mockk<EntryDataWrapper> {
            every { entry.amount } returns 15.0
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, -2)
            every { entry.timestamp } returns cal.timeInMillis
            every { preset } returns null
            every { drinkType } returns DrinkType(1, "Water", 1.0, false, 1)
        }

        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val result = viewModel.buildHistoryDayItems(
            mutableListOf(entryOne, entryTwo, entryThree),
            mutableListOf(Goal(1, 80, calendar.timeInMillis)),
            mapOf()
        )
        assertFalse(result.isEmpty())
        assertTrue(result.size == 2)
    }
}