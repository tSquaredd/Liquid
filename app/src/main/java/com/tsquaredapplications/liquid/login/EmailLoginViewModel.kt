package com.tsquaredapplications.liquid.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.ext.isValidEmail
import com.tsquaredapplications.liquid.ext.isValidPassword
import com.tsquaredapplications.liquid.login.resources.EmailLoginResourceWrapper
import javax.inject.Inject

class EmailLoginViewModel
@Inject constructor(private val resourceWrapper: EmailLoginResourceWrapper) : ViewModel() {

    private val stateLiveData = SingleEventLiveData<EmailLoginState>()
    internal fun getStateLiveData(): LiveData<EmailLoginState> = stateLiveData

    private val loginButtonEnabledLiveData = SingleEventLiveData<Boolean>().apply { value = false }
    fun getLoginButtonEnabledLiveData(): LiveData<Boolean> = loginButtonEnabledLiveData

    private var emailEntered = false
    private var passwordEntered = false

    fun onLoginClicked(email: CharSequence, password: CharSequence) {
        if (!email.isValidEmail() || password.isValidPassword() != PasswordValidation.Valid) {
            onFailedLogin()
        } else {
            stateLiveData.value =
                EmailLoginState.AttemptLogin(email.toString(), password.toString())
        }
    }

    fun onSuccessfulLogin() {
        stateLiveData.value = EmailLoginState.SuccessFulLogin
    }

    fun onFailedLogin() {
        stateLiveData.value = EmailLoginState.FailedLogin(
            errorMessage = resourceWrapper.getFailedLoginErrorMessage(),
            dismissButtonText = resourceWrapper.getFailedLoginErrorDismissButtonText()
        )
    }

    fun emailUpdated(email: String) {
        emailEntered = email.isNotEmpty()
        checkToEnableSignInButton()
    }

    fun passwordUpdated(password: String) {
        passwordEntered = password.isNotEmpty()
        checkToEnableSignInButton()
    }

    private fun checkToEnableSignInButton() {
        loginButtonEnabledLiveData.value = emailEntered && passwordEntered
    }
}