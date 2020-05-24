package com.tsquaredapplications.liquid.common

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import com.tsquaredapplications.liquid.login.LiquidUnit
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class UserInformation(
    val weight: Int,
    val unitPreference: LiquidUnit,
    val dailyGoalOz: Int
) : Parcelable