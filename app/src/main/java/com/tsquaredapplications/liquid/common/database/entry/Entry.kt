package com.tsquaredapplications.liquid.common.database.entry

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class Entry(
    @PrimaryKey(autoGenerate = true)
    val entryUid: Int = 0,
    var amount: Double,
    val timestamp: Long,
    val drinkTypeUid: Int,
    val presetUid: Int? = null
) : Parcelable