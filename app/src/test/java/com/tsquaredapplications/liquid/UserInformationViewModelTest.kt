package com.tsquaredapplications.liquid

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.login.LiquidUnit
import com.tsquaredapplications.liquid.login.information.UserInformationState
import com.tsquaredapplications.liquid.login.information.UserInformationViewModel
import com.tsquaredapplications.liquid.login.information.resources.UserInformationResourceWrapper
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserInformationViewModelTest: BaseViewModelTest() {

    @MockK
    lateinit var resourceWrapper: UserInformationResourceWrapper

    @RelaxedMockK
    lateinit var stateObserver: Observer<UserInformationState>

    @RelaxedMockK
    lateinit var unitObserver: Observer<LiquidUnit>

    private lateinit var viewModel: UserInformationViewModel
    private var stateSlot = slot<UserInformationState>()
    private var unitSlot = slot<LiquidUnit>()


    @BeforeAll
    fun setupBeforeAll() {
        MockKAnnotations.init(this)
        every { resourceWrapper.weightErrorMessage } returns WEIGHT_ERROR_MESSAGE
    }

    @BeforeEach
    fun setupBeforeEach() {
        clearMocks(stateObserver, unitObserver)
        stateSlot.clear()
        unitSlot.clear()
        viewModel = UserInformationViewModel(
            resourceWrapper
        ).apply {
            stateLiveData.observeForever(stateObserver)
            unitStateLiveData.observeForever(unitObserver)
        }
    }

    @Test
    fun `start - initializes to OZ`() {
        viewModel.start()

        verify(exactly = 1) { unitObserver.onChanged(capture(unitSlot)) }
        assertEquals(LiquidUnit.OZ, unitSlot.captured)
    }

    @Test
    fun `onUnitSelected - sets selected unit`() {
        viewModel.onUnitSelected(LiquidUnit.ML)

        verify(exactly = 1) { unitObserver.onChanged(capture(unitSlot)) }
        assertEquals(LiquidUnit.ML, unitSlot.captured)
    }

    @Nested
    @DisplayName("Given continue is clicked")
    inner class ContinueClicked {
        @Test
        fun `when weight is null - show error`() {
            viewModel.start()
            viewModel.onContinueClicked(null)

            assertInvalidWeighScenario()
        }

        @Test
        fun `when weight is empty - show error`() {
            viewModel.start()
            viewModel.onContinueClicked("")

            assertInvalidWeighScenario()
        }

        @Test
        fun `when weight is nan - show error`() {
            viewModel.start()
            viewModel.onContinueClicked("This is not a number")

            assertInvalidWeighScenario()
        }

        private fun assertInvalidWeighScenario(){
            verify(exactly = 1) { stateObserver.onChanged(capture(stateSlot)) }
            val state = stateSlot.captured as UserInformationState.InvalidWeight
            assertEquals(WEIGHT_ERROR_MESSAGE, state.errorMessage)
        }

        @Test
        fun `when weight is valid - send continue state`() {
            viewModel.start()
            viewModel.onContinueClicked("185")

            verify(exactly = 1) { stateObserver.onChanged(capture(stateSlot)) }
            val state = stateSlot.captured as UserInformationState.Continue
            assertEquals(185, state.weight)
            assertEquals(LiquidUnit.OZ, state.unitChoiceState)
        }
    }


    companion object {
        const val WEIGHT_ERROR_MESSAGE = "WEIGHT_ERROR_MESSAGE"
    }
}