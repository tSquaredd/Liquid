package com.tsquaredapplications.liquid.login.login.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import javax.inject.Inject

class EmailLoginResourceWrapperImpl
@Inject constructor(val context: Context) : EmailLoginResourceWrapper {

    override fun getFailedLoginErrorMessage() =
        context.getString(R.string.failed_login_error_message)

    override fun getFailedLoginErrorDismissButtonText() = context.getString(R.string.ok)
}