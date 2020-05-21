package com.tsquaredapplications.liquid.login.signup

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.ext.isValidEmail
import com.tsquaredapplications.liquid.ext.isValidPassword
import com.tsquaredapplications.liquid.login.common.EmailValidationState
import com.tsquaredapplications.liquid.login.common.PasswordValidation
import com.tsquaredapplications.liquid.login.common.PasswordValidationState
import com.tsquaredapplications.liquid.login.signup.EmailSignUpState.HideProgressBar
import com.tsquaredapplications.liquid.login.signup.resources.EmailSignUpResourceWrapper
import javax.inject.Inject

class EmailSignUpViewModel
@Inject constructor(
    private val authManager: AuthManager,
    private val resourceWrapper: EmailSignUpResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<EmailSignUpState>()
    val stateLiveData: LiveData<EmailSignUpState>
        get() = state

    private val emailValidationState = SingleEventLiveData<EmailValidationState>()
    val emailValidationLiveData: LiveData<EmailValidationState>
        get() = emailValidationState

    private val passwordValidationState = SingleEventLiveData<PasswordValidationState>()
    val passwordValidationLiveData: LiveData<PasswordValidationState>
        get() = passwordValidationState

    fun validateEmail(email: CharSequence) {
        emailValidationState.value = if (email.isValidEmail()) {
            EmailValidationState.Valid
        } else {
            EmailValidationState.Invalid(
                resourceWrapper.getEmailErrorMessage()
            )
        }
    }

    fun validatePassword(password: CharSequence) {
        passwordValidationState.value = when (password.isValidPassword()) {
            PasswordValidation.Valid -> PasswordValidationState.Valid
            PasswordValidation.InvalidTooShort -> PasswordValidationState.Invalid(
                resourceWrapper.getPasswordGenericErrorMessage()
            )
            PasswordValidation.InvalidTooLong -> PasswordValidationState.Invalid(
                resourceWrapper.getPasswordTooLongErrorMessage()
            )
            PasswordValidation.InvalidNoUpperCase -> PasswordValidationState.Invalid(
                resourceWrapper.getPasswordGenericErrorMessage()
            )
        }
    }

    fun onSignUpCLicked(email: CharSequence, password: CharSequence) {
        validateEmail(email)
        validatePassword(password)

        if (emailValidationState.value == EmailValidationState.Valid &&
            passwordValidationState.value == PasswordValidationState.Valid
        ) {
            state.value = EmailSignUpState.ShowProgressBar

            authManager.signUpWith(email.toString(), password.toString(),
                onComplete = {
                    onSignUpComplete()
                },
                onSuccess = {
                    onSignUpSuccess()
                },
                onFailure = { exception ->
                    onFailedSignUp(exception)
                })
        }
    }

    @VisibleForTesting
    fun onSignUpComplete() {
        state.value = HideProgressBar
    }

    @VisibleForTesting
    fun onSignUpSuccess() {
        state.value = EmailSignUpState.SuccessfulSignUp
    }

    @VisibleForTesting
    fun onFailedSignUp(exception: Exception?) {
        state.value = EmailSignUpState.FailedSignUp(
            errorMessage = if (exception is FirebaseAuthUserCollisionException) {
                resourceWrapper.getSignUpUserCollisionErrorMessage()
            } else {
                resourceWrapper.getSignUpGenericErrorMessage()
            },
            dismissButtonText = resourceWrapper.getSignUpErrorDismissButtonText()
        )
    }
}