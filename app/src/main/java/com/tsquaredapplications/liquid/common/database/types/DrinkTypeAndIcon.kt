package com.tsquaredapplications.liquid.common.database.types

import androidx.room.Embedded
import androidx.room.Relation
import com.tsquaredapplications.liquid.common.database.icons.Icon

data class DrinkTypeAndIcon(
    @Embedded
    val drinkType: DrinkType,
    @Relation(parentColumn = "drinkTypeUid", entityColumn = "iconUid")
    val icon: Icon
)