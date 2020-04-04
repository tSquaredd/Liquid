package com.tsquaredapplications.liquid.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Entry(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "type_name") val typeName: String,
    val amount: Double,
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int
) {
    fun update(
        typeName: String? = null,
        amount: Double? = null,
        year: Int? = null,
        month: Int? = null,
        day: Int? = null,
        hour: Int? = null,
        minute: Int? = null
    ) = Entry(
        this.uid,
        typeName ?: this.typeName,
        amount ?: this.amount,
        year ?: this.year,
        month ?: this.month,
        day ?: this.day,
        hour ?: this.hour,
        minute ?: this.minute
    )
}