package com.tsquaredapplications.liquid.login.login

import com.tsquaredapplications.liquid.common.auth.AuthManager

sealed class EmailLoginState {
    object SuccessFulLogin : EmailLoginState()
    class FailedLogin(val errorMessage: String, val dismissButtonText: String) : EmailLoginState()
    class ForgotPassword(val authManager: AuthManager) : EmailLoginState()
    object AbandonedSignUp : EmailLoginState()
    object ShowProgressBar : EmailLoginState()
    object HideProgressBar : EmailLoginState()
}