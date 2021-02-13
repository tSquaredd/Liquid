package com.tsquaredapplications.liquid.settings.goal.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoalSettingResourceWrapperImpl
@Inject constructor(
    @ApplicationContext private val context: Context) : GoalSettingResourceWrapper {
    override val amountErrorMessage: String
        get() = context.getString(R.string.amount_error_message)
}