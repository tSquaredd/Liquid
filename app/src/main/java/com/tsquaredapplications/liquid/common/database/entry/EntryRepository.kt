package com.tsquaredapplications.liquid.common.database.entry

import com.tsquaredapplications.liquid.common.database.presets.Preset

interface EntryRepository {
    suspend fun getAll(): List<EntryDataWrapper>
    suspend fun getAllInTimeRange(from: Long, to: Long): List<EntryDataWrapper>
    suspend fun insert(entry: Entry)
    suspend fun insertAll(entries: List<Entry>)
    suspend fun update(entry: Entry)
    suspend fun delete(entry: Entry)
    suspend fun presetRemoval(preset: Preset)
}