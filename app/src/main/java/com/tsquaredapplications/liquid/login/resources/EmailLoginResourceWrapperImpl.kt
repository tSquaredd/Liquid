package com.tsquaredapplications.liquid.login.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import javax.inject.Inject

class EmailLoginResourceWrapperImpl
@Inject constructor(val context: Context) : EmailLoginResourceWrapper {
    override fun getEmailInputErrorText() = context.getString(R.string.email_input_error_msg)
}