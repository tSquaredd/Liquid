package com.tsquaredapplications.liquid.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface EntryDao {
    @Query("SELECT* FROM entry")
    fun getAll(): List<Entry>

    @Query("SELECT * FROM entry WHERE year IS :year AND month IS :month AND day IS :day")
    fun getAllForDay(year: Int, month: Int, day: Int): List<Entry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entry: Entry)

    @Delete
    fun delete(entry: Entry)
}