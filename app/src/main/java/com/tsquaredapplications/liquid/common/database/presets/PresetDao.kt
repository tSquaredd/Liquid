package com.tsquaredapplications.liquid.common.database.presets

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PresetDao {

    @Query("SELECT * FROM preset")
    suspend fun getAllPresets(): List<Preset>

    @Insert
    suspend fun insert(preset: Preset)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(presets: List<Preset>)

    @Delete
    suspend fun delete(preset: Preset)

    @Update
    suspend fun update(preset: Preset)
}