package com.tsquaredapplications.liquid.add.drink

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.LiquidUnit
import javax.inject.Inject

class SelectDrinkResourceWrapperImpl
@Inject constructor(val context: Context) : SelectDrinkResourceWrapper {
    override fun getWarningCalculations(unit: LiquidUnit): String =
        if (unit == LiquidUnit.OZ) context.getString(R.string.alcohol_warning_calculations_oz)
        else context.getString(R.string.alcohol_warning_calculations_ml)

    override fun getSuggestion(unit: LiquidUnit): String =
        if (unit == LiquidUnit.OZ) context.getString(R.string.alcohol_warning_suggestion_oz)
        else context.getString(R.string.alcohol_warning_suggestion_ml)
}