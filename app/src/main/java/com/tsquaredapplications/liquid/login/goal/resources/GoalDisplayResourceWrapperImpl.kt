package com.tsquaredapplications.liquid.login.goal.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import javax.inject.Inject

class GoalDisplayResourceWrapperImpl
@Inject constructor(val context: Context) : GoalDisplayResourceWrapper {

    override val errorMessage: String
        get() = context.getString(R.string.data_upload_error_message)

    override val dismissErrorText: String
        get() = context.getString(R.string.ok)
}