package com.tsquaredapplications.liquid.login.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.common.database.UserDatabaseManager
import com.tsquaredapplications.liquid.ext.isValidEmail
import com.tsquaredapplications.liquid.ext.isValidPassword
import com.tsquaredapplications.liquid.login.common.PasswordValidation
import com.tsquaredapplications.liquid.login.login.EmailLoginState.AbandonedSignUp
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
    private val userDatabaseManager: UserDatabaseManager,
    private val resourceWrapper: EmailLoginResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<EmailLoginState>()
    val stateLiveData: LiveData<EmailLoginState>
        get() = state

    private val loginButtonEnabledState = SingleEventLiveData<Boolean>().apply { value = false }
    val loginButtonEnabledLiveData: LiveData<Boolean>
        get() = loginButtonEnabledState

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
                onSuccess = {
                    getUserInformation()
                },
                onFailure = {
                    onFailedLogin()
                })
        }
    }

    @VisibleForTesting
    fun getUserInformation() {
        authManager.getCurrentUser()?.let {
            userDatabaseManager.getUser(
                onSuccess = {
                    onSuccessfulLogin()
                },
                onFail = {
                    handleAbandonedSignUp()
                })
        } ?: run { onFailedLogin() }
    }

    @VisibleForTesting
    fun handleAbandonedSignUp() {
        state.value = AbandonedSignUp
    }

    @VisibleForTesting
    fun onSuccessfulLogin() {
        state.value = SuccessFulLogin
    }

    @VisibleForTesting
    fun onFailedLogin() {
        state.value = HideProgressBar
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
        loginButtonEnabledState.value = emailEntered && passwordEntered
    }

    fun forgotPasswordClicked() {
        state.value = ForgotPassword(authManager)
    }
}