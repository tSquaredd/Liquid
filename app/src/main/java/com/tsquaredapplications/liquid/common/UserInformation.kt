package com.tsquaredapplications.liquid.common

import com.google.firebase.firestore.IgnoreExtraProperties
import com.tsquaredapplications.liquid.login.LiquidUnit

@IgnoreExtraProperties
data class UserInformation(
    val weight: Int,
    val unitPreference: LiquidUnit,
    val dailyGoalOz: Int
)