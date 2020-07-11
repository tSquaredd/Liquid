package com.tsquaredapplications.liquid.setup.goal

import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.common.notifications.NotificationManager
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayState.Initialized
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayState.UserInformationSaved
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GoalDisplayViewModelTest : BaseViewModelTest<GoalDisplayState>() {

    private val userManager = mockk<UserManager>(relaxed = true)
    private val notificationManager = mockk<NotificationManager>(relaxed = true)

    lateinit var viewModel: GoalDisplayViewModel

    @BeforeAll
    fun beforeAll() {
        MockKAnnotations.init(this)
    }

    @BeforeEach
    fun beforeEach() {
        clearMocks(stateObserver)
        stateList.clear()
        viewModel = GoalDisplayViewModel(userManager, notificationManager).apply {
            stateLiveData.observeForever(stateObserver)
        }
    }

    @Test
    fun `start - returns initialized state with goal`() {
        viewModel.start(weight = 180, unit = LiquidUnit.OZ)

        verify(exactly = 1) { stateObserver.onChanged(capture(stateList)) }
        val state = stateList.first() as Initialized
        assertEquals("90 OZ", state.goal)
    }

    @Nested
    @DisplayName("Given user clicks finish")
    inner class OnFinishClick {

        @BeforeEach
        fun beforeEach() {
            viewModel.start(weight = 180, unit = LiquidUnit.OZ)
        }

        @Test
        fun `when db succeeds then send data success state`() {
            val userInformation = mockk<UserInformation>()
            every { userManager.setUser(userInformation) } returns Unit
            viewModel.onFinishClick()

            verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(Initialized::class, UserInformationSaved::class)
        }
    }

    companion object {
        const val ERROR_MESSAGE = "ERROR_MESSAGE"
        const val DISMISS_TEXT = "DISMISS_TEXT"
    }
}