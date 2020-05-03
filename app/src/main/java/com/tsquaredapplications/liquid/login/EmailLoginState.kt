package com.tsquaredapplications.liquid.login

sealed class EmailLoginState {
    class InvalidEmail(val errorMessage: String) : EmailLoginState()
}