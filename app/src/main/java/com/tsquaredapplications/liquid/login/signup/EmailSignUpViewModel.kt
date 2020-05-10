package com.tsquaredapplications.liquid.login.signup

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.ext.isValidEmail
import com.tsquaredapplications.liquid.ext.isValidPassword
import com.tsquaredapplications.liquid.login.signup.resources.EmailSignUpResourceWrapper
import com.tsquaredapplications.liquid.login.common.EmailValidationState
import com.tsquaredapplications.liquid.login.common.PasswordValidation
import com.tsquaredapplications.liquid.login.common.PasswordValidationState
import javax.inject.Inject

class EmailSignUpViewModel
@Inject constructor(
    private val auth: FirebaseAuth,
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
            auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener { task ->
                    onCreateUserResult(task)
                }
        }
    }

    @VisibleForTesting
    fun onCreateUserResult(task: Task<AuthResult>) {
        stateLiveData.value = EmailSignUpState.HideProgressBar
        if (task.isSuccessful) {
            onSignUpSuccess()
        } else {
            onFailedSignUp(task.exception)
        }
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