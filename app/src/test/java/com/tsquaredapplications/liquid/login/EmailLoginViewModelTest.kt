package com.tsquaredapplications.liquid.login

import androidx.lifecycle.Observer
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.common.database.UserDatabaseManager
import com.tsquaredapplications.liquid.ext.assertOneState
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.login.common.EmailValidationState
import com.tsquaredapplications.liquid.login.login.EmailLoginState
import com.tsquaredapplications.liquid.login.login.EmailLoginState.AbandonedSignUp
import com.tsquaredapplications.liquid.login.login.EmailLoginState.FailedLogin
import com.tsquaredapplications.liquid.login.login.EmailLoginState.ForgotPassword
import com.tsquaredapplications.liquid.login.login.EmailLoginState.HideProgressBar
import com.tsquaredapplications.liquid.login.login.EmailLoginState.ShowProgressBar
import com.tsquaredapplications.liquid.login.login.EmailLoginState.SuccessFulLogin
import com.tsquaredapplications.liquid.login.login.EmailLoginViewModel
import com.tsquaredapplications.liquid.login.login.resources.EmailLoginResourceWrapper
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
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

    @MockK
    lateinit var authManager: AuthManager

    @MockK
    lateinit var userDatabaseManager: UserDatabaseManager

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

        viewModel = EmailLoginViewModel(
            authManager,
            userDatabaseManager,
            resourceWrapper
        ).apply {
            stateLiveData.observeForever(stateObserver)
            loginButtonEnabledLiveData.observeForever(buttonEnabledObserver)
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
            assertFailedLogin()
        }

        @Test
        @DisplayName("when password is invalid then return failed login state")
        fun testLoginClickedWithInvalidPassword() {
            viewModel.onLoginClicked(VALID_EMAIL, INVALID_PASSWORD)

            verify { stateObserver.onChanged(capture(stateList)) }
            assertFailedLogin()
        }

        @Test
        @DisplayName("when email and password are invalid then return failed login state")
        fun testLoginCLickedWithInvalidEmailAndPassword() {
            viewModel.onLoginClicked(INVALID_EMAIL, INVALID_PASSWORD)

            verify { stateObserver.onChanged(capture(stateList)) }
            assertFailedLogin()
        }

        private fun assertFailedLogin() {
            stateList.assertStateOrder(
                HideProgressBar::class,
                FailedLogin::class
            )
            val failedLoginState = stateList[1] as FailedLogin
            assertEquals(FAILED_LOGIN_ERROR_MESSAGE, failedLoginState.errorMessage)
            assertEquals(FAILED_LOGIN_DISMISS_BUTTON_TEXT, failedLoginState.dismissButtonText)
        }

        @Nested
        @DisplayName("when email and password are valid")
        inner class ValidEmailPassword {

            @Test
            fun `when login fails send failed login state`() {
                every {
                    authManager.loginWith(
                        VALID_EMAIL,
                        VALID_PASSWORD,
                        any(),
                        any(),
                        any()
                    )
                } answers {
                    viewModel.onFailedLogin()
                }

                viewModel.onLoginClicked(VALID_EMAIL, VALID_PASSWORD)

                verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
                stateList.assertStateOrder(
                    ShowProgressBar::class,
                    HideProgressBar::class,
                    FailedLogin::class
                )
            }

            @Nested
            @DisplayName("given login is successful")
            inner class SuccessfulLogin {
                @BeforeEach
                fun beforeEach() {
                    every {
                        authManager.loginWith(
                            VALID_EMAIL,
                            VALID_PASSWORD,
                            any(),
                            any(),
                            any()
                        )
                    } answers {
                        viewModel.getUserInformation()
                    }

                    every { authManager.getCurrentUser() } returns mockk {
                        every { uid } returns USER_ID
                    }
                }

                @Test
                fun `when user does not exist in database send abandoned sign up state`() {
                    every { userDatabaseManager.getUser(any(), any(), any()) } answers {
                        viewModel.handleAbandonedSignUp()
                    }

                    viewModel.onLoginClicked(VALID_EMAIL, VALID_PASSWORD)

                    verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
                    stateList.assertStateOrder(
                        ShowProgressBar::class,
                        AbandonedSignUp::class
                    )
                }

                @Test
                fun `when user exists in database send successful login state`() {
                    every { userDatabaseManager.getUser(any(), any(), any()) } answers {
                        viewModel.onSuccessfulLogin()
                    }

                    viewModel.onLoginClicked(VALID_EMAIL, VALID_PASSWORD)

                    verify(exactly = 2) { stateObserver.onChanged(capture(stateList)) }
                    stateList.assertStateOrder(
                        ShowProgressBar::class,
                        SuccessFulLogin::class
                    )
                }
            }
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

    @Test
    fun `given forgot password is clicked then return forgot password state`() {
        viewModel.forgotPasswordClicked()

        verify { stateObserver.onChanged(capture(stateList)) }
        stateList.assertOneState()
        val state = stateList.first()
        assertTrue { state is ForgotPassword }
        assertEquals(authManager, (state as ForgotPassword).authManager)
    }

    companion object {
        const val VALID_EMAIL = "email@valid.com"
        const val INVALID_EMAIL = "invalidEmail"

        const val VALID_PASSWORD = "ValidPassword"
        const val INVALID_PASSWORD = "invalidpassword"

        const val FAILED_LOGIN_ERROR_MESSAGE = "FAILED_LOGIN_ERROR_MESSAGE"
        const val FAILED_LOGIN_DISMISS_BUTTON_TEXT = "FAILED_LOGIN_DISMISS_BUTTON_TEXT"

        const val USER_ID = "USER_ID"
    }
}