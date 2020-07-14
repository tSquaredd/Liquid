package com.tsquaredapplications.liquid.common.database.presets

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.ext.toTwoDigitDecimalString
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Preset(
    @PrimaryKey(autoGenerate = true)
    val presetUid: Int = 0,
    var name: String,
    var amount: Double,
    val drinkTypeUid: Int,
    var iconUid: Int
) : Parcelable {

    fun createAmountString(unitPreference: LiquidUnit): String {
        val sizeString = amount.toTwoDigitDecimalString()
        return "$sizeString $unitPreference"
    }
}