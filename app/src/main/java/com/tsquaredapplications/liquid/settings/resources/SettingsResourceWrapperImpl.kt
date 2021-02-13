package com.tsquaredapplications.liquid.settings.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsResourceWrapperImpl
@Inject constructor(
    @ApplicationContext val context: Context) : SettingsResourceWrapper {
    override val dailyGoal: String
        get() = context.getString(R.string.daily_goal)

    override val weight: String
        get() = context.getString(R.string.weight)

    override val preferredMeasurement: String
        get() = context.getString(R.string.preferred_measurement)

    override val allowReminders: String
        get() = context.getString(R.string.allow_reminders)

    override val on: String
        get() = context.getString(R.string.on)

    override val off: String
        get() = context.getString(R.string.off)

    override val rateThisApp: String
        get() = context.getString(R.string.rate_this_app)

    override val lbs: String
        get() = context.getString(R.string.lbs)
}