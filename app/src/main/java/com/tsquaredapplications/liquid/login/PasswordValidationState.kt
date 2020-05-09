package com.tsquaredapplications.liquid.login

sealed class PasswordValidationState {
    object Valid: PasswordValidationState()
    class Invalid(val errorMessage: String): PasswordValidationState()
}