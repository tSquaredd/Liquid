package com.tsquaredapplications.liquid.setup.information.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import javax.inject.Inject

class UserInformationResourceWrapperImpl
@Inject constructor(val context: Context) :
    UserInformationResourceWrapper {
    override val weightErrorMessage: String
        get() = context.getString(R.string.weight_error_message)
}