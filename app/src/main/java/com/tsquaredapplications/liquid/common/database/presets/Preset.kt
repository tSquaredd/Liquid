package com.tsquaredapplications.liquid.common.database.presets

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsquaredapplications.liquid.setup.LiquidUnit
import com.tsquaredapplications.liquid.setup.convertOzToMl
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

@Parcelize
@Entity
data class Preset(
    @PrimaryKey(autoGenerate = true)
    val presetUid: Int = 0,
    var name: String,
    var sizeInOz: Double,
    val drinkTypeUid: Int,
    var iconUid: Int
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