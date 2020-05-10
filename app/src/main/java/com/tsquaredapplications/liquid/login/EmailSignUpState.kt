package com.tsquaredapplications.liquid.login

sealed class EmailSignUpState {
    object SuccessfulSignUp : EmailSignUpState()
    class FailedSignUp(val errorMessage: String, val dismissButtonText: String) : EmailSignUpState()
    object ShowProgressBar : EmailSignUpState()
    object HideProgressBar : EmailSignUpState()
}