package com.tsquaredapplications.liquid.common.database.types

import android.os.Parcelable
import com.tsquaredapplications.liquid.common.database.icons.Icon
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DrinkTypeAndIcon(
    val drinkType: DrinkType,
    val icon: Icon
) : Parcelable