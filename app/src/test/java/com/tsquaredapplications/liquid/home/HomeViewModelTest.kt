package com.tsquaredapplications.liquid.home

import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.home.model.HomeState
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.setup.LiquidUnit
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class HomeViewModelTest : BaseViewModelTest<HomeState>() {


    private val resourceWrapper = mockk<HomeResourceWrapper>()
    private val userInformation = mockk<UserInformation> {
        every { weight } returns WEIGHT
        every { dailyGoal } returns GOAL
        every { unitPreference } returns UNIT
    }

    private val entryRepository = mockk<EntryRepository>()

    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    fun beforeEach() {
        viewModel = HomeViewModel(userInformation, resourceWrapper, entryRepository)
        viewModel.stateLiveData.observeForever(stateObserver)
    }


    companion object {
        private const val EMPTY_PROGRESS_TEXT = "0 of 100 oz"
        private const val WEIGHT = 200
        private const val GOAL = 100
        private val UNIT = LiquidUnit.OZ
    }
}