package com.tsquaredapplications.liquid.login.common

sealed class EmailValidationState {
    class Invalid(val errorMessage: String): EmailValidationState()
    object Valid: EmailValidationState()
}