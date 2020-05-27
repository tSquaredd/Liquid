package com.tsquaredapplications.liquid.login

import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.tsquaredapplications.liquid.common.BaseViewModelTest
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.ext.assertStateOrder
import com.tsquaredapplications.liquid.login.common.EmailValidationState
import com.tsquaredapplications.liquid.login.common.PasswordValidationState
import com.tsquaredapplications.liquid.login.signup.EmailSignUpState
import com.tsquaredapplications.liquid.login.signup.EmailSignUpState.FailedSignUp
import com.tsquaredapplications.liquid.login.signup.EmailSignUpState.HideProgressBar
import com.tsquaredapplications.liquid.login.signup.EmailSignUpState.ShowProgressBar
import com.tsquaredapplications.liquid.login.signup.EmailSignUpState.SuccessfulSignUp
import com.tsquaredapplications.liquid.login.signup.EmailSignUpViewModel
import com.tsquaredapplications.liquid.login.signup.resources.EmailSignUpResourceWrapper
import io.mockk.MockKAnnotations
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class EmailSignUpViewModelTest : BaseViewModelTest() {

    @MockK
    lateinit var resourceWrapper: EmailSignUpResourceWrapper

    @MockK
    lateinit var authManager: AuthManager

    @RelaxedMockK
    lateinit var stateObserver: Observer<EmailSignUpState>

    @RelaxedMockK
    lateinit var emailValidationStateObserver: Observer<EmailValidationState>

    @RelaxedMockK
    lateinit var passwordValidationStateObserver: Observer<PasswordValidationState>

    private lateinit var viewModel: EmailSignUpViewModel
    private var stateList = mutableListOf<EmailSignUpState>()
    private var emailValidationStateSlot = slot<EmailValidationState>()
    private var passwordValidationStateSlot = slot<PasswordValidationState>()

    @BeforeAll
    fun initialSetup() {
        MockKAnnotations.init(this)
        every { resourceWrapper.getEmailErrorMessage() } returns EMAIL_ERROR_MESSAGE
        every { resourceWrapper.getPasswordGenericErrorMessage() } returns PASSWORD_GENERIC_ERROR_MESSAGE
        every { resourceWrapper.getPasswordTooLongErrorMessage() } returns PASSWORD_TOO_LONG_ERROR_MESSAGE
        every { resourceWrapper.getSignUpErrorDismissButtonText() } returns SIGN_UP_ERROR_DISMISS_BUTTON_TEXT
        every { resourceWrapper.getSignUpGenericErrorMessage() } returns SIGN_UP_GENERIC_ERROR_MESSAGE
        every { resourceWrapper.getSignUpUserCollisionErrorMessage() } returns SIGN_UP_USER_COLLISION_ERROR_MESSAGE
    }

    @BeforeEach
    fun setup() {
        clearMocks(stateObserver, emailValidationStateObserver, passwordValidationStateObserver)
        stateList.clear()
        emailValidationStateSlot.clear()
        passwordValidationStateSlot.clear()

        viewModel = EmailSignUpViewModel(
            authManager,
            resourceWrapper
        ).apply {
            stateLiveData.observeForever(stateObserver)
            passwordValidationLiveData.observeForever(passwordValidationStateObserver)
            emailValidationLiveData.observeForever(emailValidationStateObserver)
        }
    }

    @Nested
    @DisplayName("Given email is validated")
    inner class EmailValidation {
        @Test
        fun `when email is valid return valid email state`() {
            viewModel.validateEmail(VALID_EMAIL)

            verify { emailValidationStateObserver.onChanged(capture(emailValidationStateSlot)) }
            assertTrue { emailValidationStateSlot.captured is EmailValidationState.Valid }
        }

        @Test
        fun `when email is invalid return invalid email state`() {
            viewModel.validateEmail(INVALID)

            verify { emailValidationStateObserver.onChanged(capture(emailValidationStateSlot)) }
            val validationState = emailValidationStateSlot.captured as EmailValidationState.Invalid
            assertEquals(EMAIL_ERROR_MESSAGE, validationState.errorMessage)
        }
    }


    @Nested
    @DisplayName("Given password is validated")
    inner class PasswordValidation {
        @Test
        fun `when password is valid return valid password state`() {
            viewModel.validatePassword(VALID_PASSWORD)

            verify { passwordValidationStateObserver.onChanged(capture(passwordValidationStateSlot)) }
            assertTrue { passwordValidationStateSlot.captured is PasswordValidationState.Valid }
        }

        @Test
        fun `when password is too short return invalid state with generic error`() {
            viewModel.validatePassword("Short")

            verify { passwordValidationStateObserver.onChanged(capture(passwordValidationStateSlot)) }
            val invalidState =
                passwordValidationStateSlot.captured as PasswordValidationState.Invalid
            assertEquals(PASSWORD_GENERIC_ERROR_MESSAGE, invalidState.errorMessage)
        }

        @Test
        fun `when password has no upper case return invalid state with generic error`() {
            viewModel.validatePassword(INVALID)

            verify { passwordValidationStateObserver.onChanged(capture(passwordValidationStateSlot)) }
            val invalidState =
                passwordValidationStateSlot.captured as PasswordValidationState.Invalid
            assertEquals(PASSWORD_GENERIC_ERROR_MESSAGE, invalidState.errorMessage)
        }

        @Test
        fun `when password is too long return invalid state with too long error message`() {
            viewModel.validatePassword("aReallyLongInvalidPasswordThatHasASpecificErrorMessage")

            verify { passwordValidationStateObserver.onChanged(capture(passwordValidationStateSlot)) }
            val invalidState =
                passwordValidationStateSlot.captured as PasswordValidationState.Invalid
            assertEquals(PASSWORD_TOO_LONG_ERROR_MESSAGE, invalidState.errorMessage)
        }
    }

    @Nested
    @DisplayName("Given user clicks sign up button")
    inner class SignUpTests {

        @Test
        fun `when email is invalid return invalid state`() {
            viewModel.onSignUpClicked(INVALID, VALID_PASSWORD)
            verify(exactly = 1) {
                emailValidationStateObserver.onChanged(
                    capture(
                        emailValidationStateSlot
                    )
                )
            }
        }

        @Test
        fun `when password is invalid return invalid state`() {
            viewModel.onSignUpClicked(VALID_EMAIL, INVALID)
            verify(exactly = 1) {
                passwordValidationStateObserver.onChanged(
                    capture(
                        passwordValidationStateSlot
                    )
                )
            }
        }

        @Nested
        @DisplayName("when email and password are valid and sign up is successful")
        inner class ValidEmailPassword {

            @Test
            fun `if sign up succeeds send successful state`() {
                every {
                    authManager.signUpWith(
                        VALID_EMAIL,
                        VALID_PASSWORD,
                        any(),
                        any(),
                        any()
                    )
                } answers {
                    viewModel.onSignUpComplete()
                    viewModel.onSignUpSuccess()
                }

                viewModel.onSignUpClicked(VALID_EMAIL, VALID_PASSWORD)
                verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
                stateList.assertStateOrder(
                    ShowProgressBar::class,
                    HideProgressBar::class,
                    SuccessfulSignUp::class
                )
            }

            @Test
            fun `if sign up fails becuase user exists send user failed state with collision message`() {
                every {
                    authManager.signUpWith(
                        VALID_EMAIL,
                        VALID_PASSWORD,
                        any(),
                        any(),
                        any()
                    )
                } answers {
                    viewModel.onSignUpComplete()
                    viewModel.onFailedSignUp(mockk<FirebaseAuthUserCollisionException>())
                }

                viewModel.onSignUpClicked(VALID_EMAIL, VALID_PASSWORD)
                verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
                stateList.assertStateOrder(
                    ShowProgressBar::class,
                    HideProgressBar::class,
                    FailedSignUp::class
                )

                val failedSignUpState = stateList[2] as FailedSignUp
                assertEquals(SIGN_UP_USER_COLLISION_ERROR_MESSAGE, failedSignUpState.errorMessage)
                assertEquals(SIGN_UP_ERROR_DISMISS_BUTTON_TEXT, failedSignUpState.dismissButtonText)
            }

            @Test
            fun `if sign up fails for unknown reason send failed state with generic message`() {
                every {
                    authManager.signUpWith(
                        VALID_EMAIL,
                        VALID_PASSWORD,
                        any(),
                        any(),
                        any()
                    )
                } answers {
                    viewModel.onSignUpComplete()
                    viewModel.onFailedSignUp(mockk())
                }

                viewModel.onSignUpClicked(VALID_EMAIL, VALID_PASSWORD)
                verify(exactly = 3) { stateObserver.onChanged(capture(stateList)) }
                stateList.assertStateOrder(
                    ShowProgressBar::class,
                    HideProgressBar::class,
                    FailedSignUp::class
                )

                val failedSignUpState = stateList[2] as FailedSignUp
                assertEquals(SIGN_UP_GENERIC_ERROR_MESSAGE, failedSignUpState.errorMessage)
                assertEquals(SIGN_UP_ERROR_DISMISS_BUTTON_TEXT, failedSignUpState.dismissButtonText)
            }
        }

    }

    companion object {
        const val EMAIL_ERROR_MESSAGE = "EMAIL_ERROR_MESSAGE"
        const val PASSWORD_GENERIC_ERROR_MESSAGE = "PASSWORD_GENERIC_ERROR_MESSAGE"
        const val PASSWORD_TOO_LONG_ERROR_MESSAGE = "PASSWORD_TOO_LONG_ERROR_MESSAGE"
        const val SIGN_UP_ERROR_DISMISS_BUTTON_TEXT = "SIGN_UP_ERROR_DISMISS_BUTTON_TEXT"
        const val SIGN_UP_GENERIC_ERROR_MESSAGE = "SIGN_UP_GENERIC_ERROR_MESSAGE"
        const val SIGN_UP_USER_COLLISION_ERROR_MESSAGE = "SIGN_UP_USER_COLLISION_ERROR_MESSAGE"

        const val VALID_EMAIL = "valid@email.com"
        const val VALID_PASSWORD = "ValidPassword"
        const val INVALID = "invalid"

    }
}