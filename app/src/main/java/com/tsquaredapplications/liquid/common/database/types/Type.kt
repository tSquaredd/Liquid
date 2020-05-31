package com.tsquaredapplications.liquid.common.database.types

import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Type(
    val name: String = "",
    val hydration: Double = 1.0,
    val iconName: String = ""
) : Parcelable