package com.tsquaredapplications.liquid.common.database.entry

import android.os.Parcelable
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import kotlinx.android.parcel.Parcelize

@Parcelize
class EntryDataWrapper(
    val entry: Entry,
    val drinkType: DrinkType,
    val preset: Preset?,
    val icon: Icon
): Parcelable