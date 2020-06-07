package com.tsquaredapplications.liquid.common.database.icons

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Icon(
    val iconPath: String = "",
    val largeIconPath: String = ""
) : Parcelable