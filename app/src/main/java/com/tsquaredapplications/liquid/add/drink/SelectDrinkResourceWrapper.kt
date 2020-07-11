package com.tsquaredapplications.liquid.add.drink

import com.tsquaredapplications.liquid.common.LiquidUnit

interface SelectDrinkResourceWrapper {
    fun getWarningCalculations(unit: LiquidUnit): String
    fun getSuggestion(unit: LiquidUnit): String
}