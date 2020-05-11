package com.tsquaredapplications.liquid.login.signup.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.login.signup.resources.EmailSignUpResourceWrapper
import javax.inject.Inject

class EmailSignUpResourceWrapperImpl
@Inject constructor(val context: Context) :
    EmailSignUpResourceWrapper {
    override fun getEmailErrorMessage() = context.getString(R.string.email_input_error_msg)

    override fun getPasswordGenericErrorMessage() =
        context.getString(R.string.password_generic_error_msg)

    override fun getPasswordTooLongErrorMessage() =
        context.getString(R.string.password_too_long_error_msg)

    override fun getSignUpGenericErrorMessage() =
        context.getString(R.string.sign_up_generic_error_message)

    override fun getSignUpUserCollisionErrorMessage() =
        context.getString(R.string.sign_up_user_collision_error_message)

    override fun getSignUpErrorDismissButtonText() = context.getString(R.string.ok)
}