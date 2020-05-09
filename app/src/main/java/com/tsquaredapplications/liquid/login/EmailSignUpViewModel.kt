package com.tsquaredapplications.liquid.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.ext.isValidEmail
import com.tsquaredapplications.liquid.ext.isValidPassword
import javax.inject.Inject

class EmailSignUpViewModel
@Inject constructor(private val resourceWrapper: EmailSignUpResourceWrapper) : ViewModel() {

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
            EmailValidationState.Invalid(resourceWrapper.getEmailErrorMessage())
        }
    }

    fun validatePassword(password: CharSequence) {
        passwordValidationLiveData.value = when (password.isValidPassword()) {
            PasswordValidation.Valid -> PasswordValidationState.Valid
            PasswordValidation.InvalidTooShort -> PasswordValidationState.Invalid(resourceWrapper.getPasswordGenericErrorMessage())
            PasswordValidation.InvalidTooLong -> PasswordValidationState.Invalid(resourceWrapper.getPasswordTooLongErrorMessage())
            PasswordValidation.InvalidNoUpperCase -> PasswordValidationState.Invalid(resourceWrapper.getPasswordGenericErrorMessage())
        }
    }

    fun onSignUpCLicked(email: CharSequence, password: CharSequence) {
        validateEmail(email)
        validatePassword(password)

        if (emailValidationLiveData.value == EmailValidationState.Valid &&
            passwordValidationLiveData.value == PasswordValidationState.Valid
        ) {
            stateLiveData.value = EmailSignUpState.AttemptSignUp(email.toString(), password.toString())
        }
    }

    fun onSignUpSuccess() {
        stateLiveData.value = EmailSignUpState.SuccessfulSignUp
    }

}