package com.tsquaredapplications.liquid.common.database.presets

import android.os.Parcelable
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PresetDataWrapper(
    val preset: Preset,
    val icon: Icon,
    val drinkType: DrinkType
) : Parcelable