package com.tsquaredapplications.liquid.ext

import com.tsquaredapplications.liquid.common.UserInformation
import com.tsquaredapplications.liquid.login.LiquidUnit

val UserInformation.dailyGoal: Int
    get() = if (unitPreference == LiquidUnit.OZ) dailyGoalOz
    else (dailyGoalOz * 29.5375).toInt()