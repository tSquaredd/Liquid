package com.tsquaredapplications.liquid.login

sealed class EmailValidationState {
    class Invalid(val errorMessage: String): EmailValidationState()
    object Valid: EmailValidationState()
}