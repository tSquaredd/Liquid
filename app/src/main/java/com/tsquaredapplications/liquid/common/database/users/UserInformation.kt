package com.tsquaredapplications.liquid.common.database.users

import android.os.Parcelable
import com.tsquaredapplications.liquid.setup.LiquidUnit
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInformation(
    val weight: Int = 150,
    val unitPreference: LiquidUnit = LiquidUnit.OZ,
    val dailyGoal: Int = 75,
    val notifications: Boolean = true
) : Parcelable