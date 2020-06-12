package com.tsquaredapplications.liquid.common.database.presets

import com.google.firebase.firestore.IgnoreExtraProperties
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.login.LiquidUnit
import com.tsquaredapplications.liquid.login.convertOzToMl
import java.text.DecimalFormat

@IgnoreExtraProperties
data class Preset(
    val name: String = "",
    val sizeInOz: Double = 0.0,
    val drinkType: DrinkType = DrinkType(),
    val icon: Icon = Icon()
) {
    fun createAmountString(unitPreference: LiquidUnit): String {
        val sizeString = DecimalFormat("0.##").format(
            if (unitPreference == LiquidUnit.OZ) {
                sizeInOz
            } else {
                convertOzToMl(sizeInOz)
            }
        )
        return "$sizeString $unitPreference"
    }
}