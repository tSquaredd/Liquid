package com.tsquaredapplications.liquid.common.database.entry

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface EntryDao {
    @Query("SELECT * FROM entry ORDER BY timestamp DESC")
    suspend fun getAllEntries(): List<Entry>

    @Query("SELECT * FROM entry WHERE timestamp >= :from AND timestamp <= :to")
    suspend fun getAllForTimeRange(from: Long, to: Long): List<Entry>

    @Query("SELECT * FROM entry WHERE presetUid == :presetUid")
    suspend fun getAllForPreset(presetUid: Int): List<Entry>

    @Insert
    suspend fun insert(entry: Entry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<Entry>)

    @Update
    suspend fun update(entry: Entry)

    @Delete
    suspend fun delete(entry: Entry)
}