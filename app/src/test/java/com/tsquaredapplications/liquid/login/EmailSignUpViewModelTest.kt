package com.tsquaredapplications.liquid.login

import androidx.lifecycle.Observer
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.tsquaredapplications.liquid.common.BaseViewModelTest
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
    lateinit var auth: FirebaseAuth

    @RelaxedMockK
    lateinit var stateObserver: Observer<EmailSignUpState>

    @RelaxedMockK
    lateinit var emailValidationStateObserver: Observer<EmailValidationState>

    @RelaxedMockK
    lateinit var passwordValidationStateObserver: Observer<PasswordValidationState>

    private lateinit var viewModel: EmailSignUpViewModel
    private var stateSlot = slot<EmailSignUpState>()
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
        stateSlot.clear()
        emailValidationStateSlot.clear()
        passwordValidationStateSlot.clear()

        viewModel = EmailSignUpViewModel(auth, resourceWrapper).apply {
            getStateLiveData().observeForever(stateObserver)
            getPasswordValidationLiveData().observeForever(passwordValidationStateObserver)
            getEmailValidationLiveData().observeForever(emailValidationStateObserver)
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
        fun `when email is invalid then firebase auth is not called`() {
            viewModel.onSignUpCLicked(INVALID, VALID_PASSWORD)

            verify(exactly = 0) { auth.createUserWithEmailAndPassword(any(), any()) }
        }

        @Test
        fun `when password is invalid then firebase auth is not called`() {
            viewModel.onSignUpCLicked(VALID_EMAIL, INVALID)

            verify(exactly = 0) { auth.createUserWithEmailAndPassword(any(), any()) }
        }
    }

    @Nested
    @DisplayName("Given firebase auth returns from attempting to create user")
    inner class FirebaseAuthResult {

        @Test
        fun `when task was success return successful sign up state`() {
            val authTask = mockk<Task<AuthResult>> {
                every { isSuccessful } returns true
            }

            viewModel.onCreateUserResult(authTask)
            verify { stateObserver.onChanged(capture(stateSlot)) }
            assertTrue { stateSlot.captured is EmailSignUpState.SuccessfulSignUp }
        }

        @Nested
        @DisplayName("When task fails")
        inner class FailedAccountCreation {

            @Test
            fun `because user already exists, return fail state with specific error message`() {
                val authTask = mockk<Task<AuthResult>>() {
                    every { isSuccessful } returns false
                    every { exception } returns mockk<FirebaseAuthUserCollisionException>()
                }

                viewModel.onCreateUserResult(authTask)

                verify { stateObserver.onChanged(capture(stateSlot)) }
                val state = stateSlot.captured as EmailSignUpState.FailedSignUp
                assertEquals(SIGN_UP_USER_COLLISION_ERROR_MESSAGE, state.errorMessage)
                assertEquals(SIGN_UP_ERROR_DISMISS_BUTTON_TEXT, state.dismissButtonText)
            }

            @Test
            fun `for generic reason, return fail state with generic error message`() {
                val authTask = mockk<Task<AuthResult>>() {
                    every { isSuccessful } returns false
                    every { exception } returns mockk()
                }

                viewModel.onCreateUserResult(authTask)

                verify { stateObserver.onChanged(capture(stateSlot)) }
                val state = stateSlot.captured as EmailSignUpState.FailedSignUp
                assertEquals(SIGN_UP_GENERIC_ERROR_MESSAGE, state.errorMessage)
                assertEquals(SIGN_UP_ERROR_DISMISS_BUTTON_TEXT, state.dismissButtonText)
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