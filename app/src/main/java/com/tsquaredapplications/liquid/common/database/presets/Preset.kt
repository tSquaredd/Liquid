package com.tsquaredapplications.liquid.common.database.presets

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.login.LiquidUnit
import com.tsquaredapplications.liquid.login.convertOzToMl
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

@IgnoreExtraProperties
@Parcelize
data class Preset(
    var name: String = "",
    var sizeInOz: Double = 0.0,
    val drinkType: DrinkType = DrinkType(),
    var icon: Icon = Icon(),
    val dbKey: String = ""
) : Parcelable {
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