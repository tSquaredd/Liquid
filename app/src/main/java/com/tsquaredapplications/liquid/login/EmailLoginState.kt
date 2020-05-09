package com.tsquaredapplications.liquid.login

sealed class EmailLoginState {
    class AttemptLogin(val email: String, val password: String) : EmailLoginState()
    object SuccessFulLogin : EmailLoginState()
    class FailedLogin(val errorMessage: String, val dismissButtonText: String) : EmailLoginState()
}