package com.tsquaredapplications.liquid.login

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.login.resources.EmailLoginResourceWrapper
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailLoginViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var resourceWrapper: EmailLoginResourceWrapper

    @RelaxedMockK
    lateinit var stateObserver: Observer<EmailLoginState>

    @RelaxedMockK
    lateinit var buttonEnabledObserver: Observer<Boolean>

    private lateinit var viewModel: EmailLoginViewModel
    private var stateList = mutableListOf<EmailLoginState>()
    private var buttonStateSlot = mutableListOf<Boolean>()
    private var emailValidationStateList = mutableListOf<EmailValidationState>()

    @BeforeAll
    fun initialSetup() {
        MockKAnnotations.init(this)
        every { resourceWrapper.getFailedLoginErrorMessage() } returns FAILED_LOGIN_ERROR_MESSAGE
        every { resourceWrapper.getFailedLoginErrorDismissButtonText() } returns FAILED_LOGIN_DISMISS_BUTTON_TEXT
    }

    @BeforeEach
    fun setup() {
        clearMocks(stateObserver, buttonEnabledObserver)
        stateList.clear()
        buttonStateSlot.clear()
        emailValidationStateList.clear()

        viewModel = EmailLoginViewModel(resourceWrapper).apply {
            getStateLiveData().observeForever(stateObserver)
            getLoginButtonEnabledLiveData().observeForever(buttonEnabledObserver)
        }
    }

    @Nested
    @DisplayName("Given login button is clicked")
    inner class LoginClicked {

        @Test
        @DisplayName("when email is invalid then return failed login state")
        fun testLoginClickedWithInvalidEmail() {
            viewModel.onLoginClicked(INVALID_EMAIL, VALID_PASSWORD)

            verify { stateObserver.onChanged(capture(stateList)) }
            stateList.assertOneState()
            assertTrue { stateList[0] is EmailLoginState.FailedLogin }
        }

        @Test
        @DisplayName("when password is invalid then return failed login state")
        fun testLoginClickedWithInvalidPassword() {
            viewModel.onLoginClicked(VALID_EMAIL, INVALID_PASSWORD)

            verify { stateObserver.onChanged(capture(stateList)) }
            stateList.assertOneState()
            assertTrue { stateList[0] is EmailLoginState.FailedLogin }
        }

        @Test
        @DisplayName("when email and password are invalid then return failed login state")
        fun testLoginCLickedWithInvalidEmailAndPassword() {
            viewModel.onLoginClicked(INVALID_EMAIL, INVALID_PASSWORD)

            verify { stateObserver.onChanged(capture(stateList)) }
            stateList.assertOneState()
            assertTrue { stateList[0] is EmailLoginState.FailedLogin }
        }

        @Test
        @DisplayName("when email and password are valid then return attempt login state")
        fun testLoginClickedWithValidEmailAndPassword() {
            viewModel.onLoginClicked(VALID_EMAIL, VALID_PASSWORD)

            verify { stateObserver.onChanged(capture(stateList)) }
            stateList.assertOneState()
            assertTrue { stateList.first() is EmailLoginState.AttemptLogin }

            with(stateList.first() as EmailLoginState.AttemptLogin) {
                assertEquals(VALID_EMAIL, email)
                assertEquals(VALID_PASSWORD, password)
            }
        }
    }

    @Test
    @DisplayName("Given login is successful, then return successful login state")
    fun testOnSuccessfulLogin() {
        viewModel.onSuccessfulLogin()

        verify { stateObserver.onChanged(capture(stateList)) }
        stateList.assertOneState()
        assertTrue { stateList.first() is EmailLoginState.SuccessFulLogin }
    }

    @Test
    @DisplayName("Given failed login, then return failed login state")
    fun testOnFailedLogin() {
        viewModel.onFailedLogin()

        verify { stateObserver.onChanged(capture(stateList)) }
        stateList.assertOneState()
        assertTrue { stateList.first() is EmailLoginState.FailedLogin }
        with(stateList.first() as EmailLoginState.FailedLogin) {
            assertEquals(FAILED_LOGIN_ERROR_MESSAGE, errorMessage)
            assertEquals(FAILED_LOGIN_DISMISS_BUTTON_TEXT, dismissButtonText)
        }
    }

    @Nested
    @DisplayName("Given email or password is updated")
    inner class PasswordOrEmailUpdated {

        @Test
        @DisplayName("when email is empty then login button should be disabled")
        fun testOnEmailUpdatedEmpty() {
            viewModel.emailUpdated("")

            verify { buttonEnabledObserver.onChanged(capture(buttonStateSlot)) }
            buttonStateSlot.assertOneState()
            assertEquals(false, buttonStateSlot.first())
        }

        @Test
        @DisplayName("when password is empty then login button should be disabled")
        fun testOnPasswordUpdatedEmpty() {
            viewModel.passwordUpdated("")

            verify { buttonEnabledObserver.onChanged(capture(buttonStateSlot)) }
            buttonStateSlot.assertOneState()
            assertEquals(false, buttonStateSlot.first())
        }

        @Test
        @DisplayName("when email and password are non empty then login button should be enabled")
        fun testOnEmailAndPasswordUpdatedToNonEmpty() {
            viewModel.emailUpdated("e")
            viewModel.passwordUpdated("p")

            verify { buttonEnabledObserver.onChanged(capture(buttonStateSlot)) }
            assertEquals(2, buttonStateSlot.size)
            assertFalse { buttonStateSlot.first() }
            assertTrue { buttonStateSlot[1] }
        }

        @Test
        @DisplayName("when email and password were non empty but email is made empty then login button is disabled")
        fun testEmailRevertedToEmpty() {
            viewModel.emailUpdated("e")
            viewModel.passwordUpdated("p")
            viewModel.emailUpdated("")

            verify { buttonEnabledObserver.onChanged(capture(buttonStateSlot)) }
            assertEquals(3, buttonStateSlot.size)
            assertFalse { buttonStateSlot.first() }
            assertTrue { buttonStateSlot[1] }
            assertFalse { buttonStateSlot[2] }

        }

        @Test
        @DisplayName("when email and password were non empty but password is made empty then login button is disabled")
        fun testPasswordRevertedToEmpty() {
            viewModel.emailUpdated("e")
            viewModel.passwordUpdated("p")
            viewModel.passwordUpdated("")

            verify { buttonEnabledObserver.onChanged(capture(buttonStateSlot)) }
            assertEquals(3, buttonStateSlot.size)
            assertFalse { buttonStateSlot.first() }
            assertTrue { buttonStateSlot[1] }
            assertFalse { buttonStateSlot[2] }
        }
    }

    private fun <T> MutableList<T>.assertOneState() {
        assertEquals(1, size)
    }

    companion object {
        const val VALID_EMAIL = "email@valid.com"
        const val INVALID_EMAIL = "invalidEmail"

        const val VALID_PASSWORD = "ValidPassword"
        const val INVALID_PASSWORD = "invalidpassword"

        const val FAILED_LOGIN_ERROR_MESSAGE = "FAILED_LOGIN_ERROR_MESSAGE"
        const val FAILED_LOGIN_DISMISS_BUTTON_TEXT = "FAILED_LOGIN_DISMISS_BUTTON_TEXT"
    }
}