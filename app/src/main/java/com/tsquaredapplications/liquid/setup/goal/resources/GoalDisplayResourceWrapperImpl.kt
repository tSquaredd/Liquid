package com.tsquaredapplications.liquid.setup.goal.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoalDisplayResourceWrapperImpl
@Inject constructor(@ApplicationContext val context: Context) : GoalDisplayResourceWrapper {

    override val errorMessage: String
        get() = context.getString(R.string.data_upload_error_message)

    override val dismissErrorText: String
        get() = context.getString(R.string.got_it)
}