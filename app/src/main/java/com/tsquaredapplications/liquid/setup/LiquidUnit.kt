package com.tsquaredapplications.liquid.setup

import kotlin.math.roundToInt

enum class LiquidUnit {
    OZ, ML
}

fun calculateDailyGoal(unit: LiquidUnit, weight: Int) = if (unit == LiquidUnit.OZ) weight / 2
else ((weight / 2) * 29.5375).roundToInt()

fun convertOzToMl(oz: Double) = oz * 29.5375

fun convertMlToOz(ml: Double) = ml / 29.5375