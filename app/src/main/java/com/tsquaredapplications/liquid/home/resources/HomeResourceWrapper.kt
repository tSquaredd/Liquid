package com.tsquaredapplications.liquid.home.resources

import com.tsquaredapplications.liquid.login.LiquidUnit

interface HomeResourceWrapper {

    fun getGoalProgressText(progress: Int, goal: Int, unit: LiquidUnit): String
}