package com.tsquaredapplications.liquid.login.signup

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

    private val stateLiveData = SingleEventLiveData<EmailSignUpState>()
    fun getStateLiveData(): LiveData<EmailSignUpState> = stateLiveData

    private val emailValidationLiveData = SingleEventLiveData<EmailValidationState>()
    internal fun getEmailValidationLiveData(): LiveData<EmailValidationState> =
        emailValidationLiveData

    private val passwordValidationLiveData = SingleEventLiveData<PasswordValidationState>()
    fun getPasswordValidationLiveData(): LiveData<PasswordValidationState> =
        passwordValidationLiveData

    fun validateEmail(email: CharSequence) {
        emailValidationLiveData.value = if (email.isValidEmail()) {
            EmailValidationState.Valid
        } else {
            EmailValidationState.Invalid(
                resourceWrapper.getEmailErrorMessage()
            )
        }
    }

    fun validatePassword(password: CharSequence) {
        passwordValidationLiveData.value = when (password.isValidPassword()) {
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

        if (emailValidationLiveData.value == EmailValidationState.Valid &&
            passwordValidationLiveData.value == PasswordValidationState.Valid
        ) {
            stateLiveData.value = EmailSignUpState.ShowProgressBar

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

    private fun onSignUpComplete() {
        stateLiveData.value = HideProgressBar
    }

    private fun onSignUpSuccess() {
        stateLiveData.value = EmailSignUpState.SuccessfulSignUp
    }

    private fun onFailedSignUp(exception: Exception?) {
        stateLiveData.value = EmailSignUpState.FailedSignUp(
            errorMessage = if (exception is FirebaseAuthUserCollisionException) {
                resourceWrapper.getSignUpUserCollisionErrorMessage()
            } else {
                resourceWrapper.getSignUpGenericErrorMessage()
            },
            dismissButtonText = resourceWrapper.getSignUpErrorDismissButtonText()
        )
    }
}