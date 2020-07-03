package com.tsquaredapplications.liquid.common.database.users

import android.os.Parcelable
import com.tsquaredapplications.liquid.setup.LiquidUnit
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInformation(
    var weight: Int = 150,
    var unitPreference: LiquidUnit = LiquidUnit.OZ,
    var dailyGoal: Int = 75,
    var notifications: NotificationsPreferences = NotificationsPreferences()
) : Parcelable

@Parcelize
data class NotificationsPreferences(
    var enabled: Boolean = true,
    var startTime: NotificationTime = NotificationTime(9, 0),
    var endTime: NotificationTime = NotificationTime(21, 0),
    var intervalMins: Int = 120
) : Parcelable {
    fun copy() = NotificationsPreferences(
        this.enabled,
        this.startTime.copy(),
        this.endTime.copy(),
        this.intervalMins
    )
}

@Parcelize
data class NotificationTime(var hour: Int, var min: Int) : Parcelable