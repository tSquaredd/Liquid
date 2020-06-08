package com.tsquaredapplications.liquid.common.database.presets

import com.google.firebase.firestore.IgnoreExtraProperties
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType

@IgnoreExtraProperties
data class Preset(
    val name: String = "",
    val sizeInOz: Double = 0.0,
    val drinkType: DrinkType = DrinkType(),
    val icon: Icon = Icon()
)