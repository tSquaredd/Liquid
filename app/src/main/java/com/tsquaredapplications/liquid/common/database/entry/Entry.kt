package com.tsquaredapplications.liquid.common.database.entry

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Entry(
    @PrimaryKey(autoGenerate = true)
    val entryUid: Int = 0,
    var amount: Double,
    val timestamp: Long,
    val drinkTypeUid: Int,
    val presetUid: Int? = null
)