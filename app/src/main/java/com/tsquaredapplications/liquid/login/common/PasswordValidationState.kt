package com.tsquaredapplications.liquid.login.common

sealed class PasswordValidationState {
    object Valid: PasswordValidationState()
    class Invalid(val errorMessage: String): PasswordValidationState()
}