package com.tsquaredapplications.liquid.login

interface EmailSignUpResourceWrapper {
    fun getEmailErrorMessage(): String

    fun getPasswordGenericErrorMessage(): String

    fun getPasswordTooLongErrorMessage(): String
}