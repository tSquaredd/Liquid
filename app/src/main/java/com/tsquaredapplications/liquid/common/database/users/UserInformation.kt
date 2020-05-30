package com.tsquaredapplications.liquid.common.database.users

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import com.tsquaredapplications.liquid.login.LiquidUnit
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class UserInformation(
    val weight: Int = 150,
    val unitPreference: LiquidUnit = LiquidUnit.OZ,
    val dailyGoal: Int = 75
) : Parcelable