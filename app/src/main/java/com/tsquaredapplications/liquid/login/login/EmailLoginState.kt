package com.tsquaredapplications.liquid.login.login

sealed class EmailLoginState {
    object SuccessFulLogin : EmailLoginState()
    class FailedLogin(val errorMessage: String, val dismissButtonText: String) : EmailLoginState()
    object ShowProgressBar : EmailLoginState()
    object HideProgressBar : EmailLoginState()
}