package com.tsquaredapplications.liquid.common.database.icons

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PresetIcon(
    val iconPath: String = "",
    val largeIconPath: String = ""
) : Parcelable