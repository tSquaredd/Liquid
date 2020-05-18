package com.tsquaredapplications.liquid.login.goal

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.database.UserDatabaseManager
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.login.LiquidUnit
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.DataFail
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.DataSuccess
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.HideProgressBar
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.Initialized
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.ShowProgressBar
import com.tsquaredapplications.liquid.login.goal.resources.GoalDisplayResourceWrapper
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GoalDisplayViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var userDatabaseManager: UserDatabaseManager

    @MockK
    lateinit var resourceWrapper: GoalDisplayResourceWrapper

    @RelaxedMockK
    lateinit var stateObserver: Observer<GoalDisplayState>

    private val stateList = mutableListOf<GoalDisplayState>()
    lateinit var viewModel: GoalDisplayViewModel

    @BeforeAll
    fun beforeAll() {
        MockKAnnotations.init(this)
        every { resourceWrapper.errorMessage } returns ERROR_MESSAGE
        every { resourceWrapper.dismissErrorText } returns DISMISS_TEXT
    }

    @BeforeEach
    fun beforeEach() {
        clearMocks(stateObserver)
        stateList.clear()
        viewModel = GoalDisplayViewModel(userDatabaseManager, resourceWrapper).apply {
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
            every { userDatabaseManager.setUser(any(), any(), any(), any()) } answers {
                viewModel.onSetUserComplete()
                viewModel.onSetUserSuccess()
            }

            viewModel.onFinishClick()

            verify(exactly = 4) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                Initialized::class,
                ShowProgressBar::class,
                HideProgressBar::class,
                DataSuccess::class
            )
        }

        @Test
        fun `when db fails send data fail state`() {
            every { userDatabaseManager.setUser(any(), any(), any(), any()) } answers {
                viewModel.onSetUserComplete()
                viewModel.onSetUserFail()
            }

            viewModel.onFinishClick()

            verify(exactly = 4) { stateObserver.onChanged(capture(stateList)) }
            stateList.assertStateOrder(
                Initialized::class,
                ShowProgressBar::class,
                HideProgressBar::class,
                DataFail::class
            )

            val dataFailState = stateList[3] as DataFail
            assertEquals(ERROR_MESSAGE, dataFailState.errorMessage)
            assertEquals(DISMISS_TEXT, dataFailState.dismissText)
        }
    }

    companion object {
        const val ERROR_MESSAGE = "ERROR_MESSAGE"
        const val DISMISS_TEXT = "DISMISS_TEXT"
    }
}