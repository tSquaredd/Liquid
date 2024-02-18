package dev.tsquaredapps.liquid.data.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("ounces") val ounces: Float
)