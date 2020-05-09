package com.tsquaredapplications.liquid.login

sealed class EmailSignUpState {
    class AttemptSignUp(val email: String, val password: String): EmailSignUpState()
    object SuccessfulSignUp: EmailSignUpState()
}