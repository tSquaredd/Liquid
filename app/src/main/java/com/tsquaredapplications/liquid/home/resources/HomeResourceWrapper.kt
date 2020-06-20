package com.tsquaredapplications.liquid.home.resources

import com.tsquaredapplications.liquid.setup.LiquidUnit

interface HomeResourceWrapper {

    fun getGoalProgressText(progress: Double, goal: Int, unit: LiquidUnit): String
}