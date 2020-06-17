package com.tsquaredapplications.liquid.settings.goal.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import javax.inject.Inject

class GoalSettingResourceWrapperImpl
@Inject constructor(private val context: Context) : GoalSettingResourceWrapper {
    override val amountErrorMessage: String
        get() = context.getString(R.string.amount_error_message)
}