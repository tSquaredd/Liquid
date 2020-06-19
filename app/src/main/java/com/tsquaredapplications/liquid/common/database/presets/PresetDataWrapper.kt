package com.tsquaredapplications.liquid.common.database.presets

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PresetDataWrapper(
    @Embedded val preset: Preset,
    @Relation(parentColumn = "iconUid", entityColumn = "iconUid", entity = Icon::class)
    val icon: Icon,
    @Relation(parentColumn = "drinkTypeUid",
        entityColumn = "drinkTypeUid",
        entity = DrinkType::class
    )
    val drinkType: DrinkType
) : Parcelable