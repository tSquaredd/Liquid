package com.tsquaredapplications.liquid.home

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.UserInformation
import com.tsquaredapplications.liquid.common.database.UserDatabaseManager
import com.tsquaredapplications.liquid.home.model.HomeState
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.login.LiquidUnit
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HomeViewModelTest : BaseViewModelTest() {


    private val userDatabaseManager = mockk<UserDatabaseManager>()
    private val resourceWrapper = mockk<HomeResourceWrapper>()
    private val userInformation = mockk<UserInformation> {
        every { weight } returns WEIGHT
        every { dailyGoal } returns GOAL
        every { unitPreference } returns UNIT
    }

    private val stateObserver = mockk<Observer<HomeState>>(relaxed = true)
    private val stateList = mutableListOf<HomeState>()

    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    fun beforeEach() {
        viewModel = HomeViewModel(userDatabaseManager, resourceWrapper)
        viewModel.stateLiveData.observeForever(stateObserver)
    }

    @Test
    fun `when initialized send initialized state`() {
        every {
            resourceWrapper.getGoalProgressText(
                progress = any(),
                goal = any(),
                unit = any()
            )
        } returns EMPTY_PROGRESS_TEXT

        every { userDatabaseManager.getUser(any(), any()) } answers {
            viewModel.updateProgress(userInformation)
        }

        viewModel.start()

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        val initState = stateList.first() as HomeState.Initialized
        assertEquals(EMPTY_PROGRESS_TEXT, initState.goalProgress)
    }

    companion object {
        private const val EMPTY_PROGRESS_TEXT = "0 of 100 oz"
        private const val WEIGHT = 200
        private const val GOAL = 100
        private val UNIT = LiquidUnit.OZ
    }
}