package com.tsquaredapplications.liquid.common.database.types

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.tsquaredapplications.liquid.common.database.icons.Icon
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrinkTypeAndIcon(
    @Embedded
    val drinkType: DrinkType,
    @Relation(parentColumn = "drinkTypeUid", entityColumn = "iconUid", entity = Icon::class)
    val icon: Icon
) : Parcelable