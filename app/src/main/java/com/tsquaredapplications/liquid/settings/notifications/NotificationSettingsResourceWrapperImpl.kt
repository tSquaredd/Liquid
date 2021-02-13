package com.tsquaredapplications.liquid.settings.notifications

import android.content.Context
import com.tsquaredapplications.liquid.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationSettingsResourceWrapperImpl
    @Inject constructor(
        @ApplicationContext private val context: Context): NotificationSettingsResourceWrapper {
    override val am: String
        get() = context.getString(R.string.am)
    override val pm: String
        get() = context.getString(R.string.pm)
}