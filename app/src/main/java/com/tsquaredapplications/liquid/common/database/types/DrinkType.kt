package com.tsquaredapplications.liquid.common.database.types

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
class DrinkType(
    @PrimaryKey val drinkTypeUid: Int,
    val name: String,
    val hydration: Double,
    val isAlcohol: Boolean,
    val iconUid: Int
) : Parcelable