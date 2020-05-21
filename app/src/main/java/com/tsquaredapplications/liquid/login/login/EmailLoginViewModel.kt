package com.tsquaredapplications.liquid.login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.ext.isValidEmail
import com.tsquaredapplications.liquid.ext.isValidPassword
import com.tsquaredapplications.liquid.login.common.PasswordValidation
import com.tsquaredapplications.liquid.login.login.EmailLoginState.FailedLogin
import com.tsquaredapplications.liquid.login.login.EmailLoginState.ForgotPassword
import com.tsquaredapplications.liquid.login.login.EmailLoginState.HideProgressBar
import com.tsquaredapplications.liquid.login.login.EmailLoginState.ShowProgressBar
import com.tsquaredapplications.liquid.login.login.EmailLoginState.SuccessFulLogin
import com.tsquaredapplications.liquid.login.login.resources.EmailLoginResourceWrapper
import javax.inject.Inject

class EmailLoginViewModel
@Inject constructor(
    private val authManager: AuthManager,
    private val resourceWrapper: EmailLoginResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<EmailLoginState>()
    val stateLiveData: LiveData<EmailLoginState>
        get() = state

    private val loginButtonEnabledLiveData = SingleEventLiveData<Boolean>().apply { value = false }
    fun getLoginButtonEnabledLiveData(): LiveData<Boolean> = loginButtonEnabledLiveData

    private var emailEntered = false
    private var passwordEntered = false

    fun onLoginClicked(email: CharSequence, password: CharSequence) {
        if (!email.isValidEmail() || password.isValidPassword() != PasswordValidation.Valid) {
            onFailedLogin()
        } else {
            state.value = ShowProgressBar
            authManager.loginWith(
                email = email.toString(),
                password = password.toString(),
                onComplete = {
                    onSignInComplete()
                },
                onSuccess = {
                    onSuccessfulLogin()
                },
                onFailure = {
                    onFailedLogin()
                })
        }
    }

    private fun onSignInComplete() {
        state.value = HideProgressBar
    }

    private fun onSuccessfulLogin() {
        state.value =
            SuccessFulLogin
    }

    private fun onFailedLogin() {
        state.value = FailedLogin(
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

    fun forgotPasswordClicked() {
        state.value = ForgotPassword(authManager)
    }
}