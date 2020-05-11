package com.tsquaredapplications.liquid.login.login.resources

interface EmailLoginResourceWrapper {

    fun getFailedLoginErrorMessage(): String

    fun getFailedLoginErrorDismissButtonText(): String
}