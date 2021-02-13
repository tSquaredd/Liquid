package com.tsquaredapplications.liquid.add.drink.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.LiquidUnit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SelectDrinkResourceWrapperImpl
@Inject constructor(
    @ApplicationContext val context: Context
) :
    SelectDrinkResourceWrapper {
    override fun getWarningCalculations(unit: LiquidUnit): String =
        if (unit == LiquidUnit.OZ) context.getString(R.string.alcohol_warning_calculations_oz)
        else context.getString(R.string.alcohol_warning_calculations_ml)

    override fun getSuggestion(unit: LiquidUnit): String =
        if (unit == LiquidUnit.OZ) context.getString(R.string.alcohol_warning_suggestion_oz)
        else context.getString(R.string.alcohol_warning_suggestion_ml)
}