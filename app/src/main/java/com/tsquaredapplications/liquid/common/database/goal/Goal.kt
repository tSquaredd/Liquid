package com.tsquaredapplications.liquid.common.database.goal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Goal(
    @PrimaryKey(autoGenerate = true)
    val goalUid: Int = 0,
    val goalAmount: Int,
    val startTimeStamp: Long
)