package com.tsquaredapplications.liquid.login

import android.content.Context
import com.tsquaredapplications.liquid.R
import javax.inject.Inject

class EmailSignUpResourceWrapperImpl
    @Inject constructor(val context: Context): EmailSignUpResourceWrapper {
    override fun getEmailErrorMessage()= context.getString(R.string.email_input_error_msg)

    override fun getPasswordGenericErrorMessage() = context.getString(R.string.password_generic_error_msg)

    override fun getPasswordTooLongErrorMessage() = context.getString(R.string.password_too_long_error_msg)
}