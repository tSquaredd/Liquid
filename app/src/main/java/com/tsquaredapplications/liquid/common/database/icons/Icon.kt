package com.tsquaredapplications.liquid.common.database.icons

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Icon(
    @PrimaryKey val iconUid: Int,
    @DrawableRes val iconResource: Int,
    @DrawableRes val largeIconResource: Int
) : Parcelable