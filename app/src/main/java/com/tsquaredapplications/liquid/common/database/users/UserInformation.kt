package com.tsquaredapplications.liquid.common.database.users

import android.os.Parcelable
import com.tsquaredapplications.liquid.setup.LiquidUnit
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInformation(
    var weight: Int = 150,
    var unitPreference: LiquidUnit = LiquidUnit.OZ,
    var dailyGoal: Int = 75,
    var notifications: Boolean = true
) : Parcelable