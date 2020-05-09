package com.tsquaredapplications.liquid.login.resources

interface EmailLoginResourceWrapper {

    fun getFailedLoginErrorMessage(): String

    fun getFailedLoginErrorDismissButtonText(): String
}