package com.tsquaredapplications.liquid.settings.goal

import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.Finished
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.Initialized
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.InvalidAmount
import com.tsquaredapplications.liquid.settings.goal.resources.GoalSettingResourceWrapper
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_DAILY_GOAL
import com.tsquaredapplications.liquid.test.util.mocks.MOCK_UNIT_PREF
import com.tsquaredapplications.liquid.test.util.mocks.mockUserInformation
import com.tsquaredapplications.liquid.test.util.mocks.mockUserManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GoalSettingViewModelTest : BaseViewModelTest<GoalSettingState>() {

    lateinit var viewModel: GoalSettingViewModel

    private val userInformation = mockUserInformation()
    private val userManager = mockUserManager()
    private val resourceWrapper = mockk<GoalSettingResourceWrapper> {
        every { amountErrorMessage } returns AMOUNT_ERROR_MESSAGE
    }

    @BeforeEach
    fun beforeEach() {
        viewModel = GoalSettingViewModel(userInformation, userManager, resourceWrapper).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Test
    fun `when viewModel started set state to Initialized`() {
        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is Initialized }
        with(stateList.first() as Initialized) {
            assertEquals(MOCK_UNIT_PREF.toString(), unitPreference)
            assertEquals(MOCK_DAILY_GOAL.toString(), currentGoal)
        }
    }

    @Test
    fun `given goal is set to invalid amount, when user attempts to update goal, then set state to InvalidAmount`() {
        with(viewModel) {
            onGoalInputChanged("")
            update()
        }

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is InvalidAmount }
        with(stateList.first() as InvalidAmount) {
            assertEquals(AMOUNT_ERROR_MESSAGE, errorMessage)
        }
    }

    @Test
    fun `given goal is set to valid amount, when user attempts to update goal, then save new goal and set state to Finished`() {
        val newGoal = 150
        with(viewModel) {
            onGoalInputChanged(newGoal.toString())
            update()
        }

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        assertTrue { stateList.first() is Finished }

        val goalSlot = slot<Int>()
        verify(exactly = 1) { userManager.updateGoal(capture(goalSlot)) }
        assertEquals(newGoal, goalSlot.captured)
    }

    companion object {
        const val AMOUNT_ERROR_MESSAGE = "AMOUNT_ERROR_MESSAGE"
    }
}