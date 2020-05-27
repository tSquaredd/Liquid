package com.tsquaredapplications.liquid.common

import com.google.firebase.firestore.IgnoreExtraProperties
import com.tsquaredapplications.liquid.login.LiquidUnit

@IgnoreExtraProperties
data class UserInformation(
    val weight: Int = 150,
    val unitPreference: LiquidUnit = LiquidUnit.OZ,
    val dailyGoal: Int = 75
)