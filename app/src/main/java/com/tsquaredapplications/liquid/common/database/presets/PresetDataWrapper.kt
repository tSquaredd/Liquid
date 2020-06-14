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
    @Relation(parentColumn = "presetUid", entityColumn = "iconUid")
    val icon: Icon,
    @Relation(parentColumn = "presetUid", entityColumn = "drinkTypeUid")
    val drinkType: DrinkType
) : Parcelable