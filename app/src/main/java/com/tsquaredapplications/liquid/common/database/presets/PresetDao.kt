package com.tsquaredapplications.liquid.common.database.presets

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface PresetDao {
    @Transaction
    @Query("SELECT * FROM preset")
    suspend fun getAllPresets(): List<PresetDataWrapper>

    @Transaction
    @Insert
    suspend fun insert(preset: Preset)

    @Delete
    suspend fun delete(preset: Preset)

    @Update
    suspend fun update(preset: Preset)
}