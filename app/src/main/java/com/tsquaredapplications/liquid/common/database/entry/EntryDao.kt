package com.tsquaredapplications.liquid.common.database.entry

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EntryDao {
    @Query("SELECT * FROM entry")
    suspend fun getAllEntries(): List<EntryDataWrapper>

    @Query("SELECT * FROM entry WHERE timestamp >= :from AND timestamp <= :to")
    suspend fun getAllForTimeRange(from: Long, to: Long): List<EntryDataWrapper>

    @Insert
    suspend fun insert(entry: Entry)
}