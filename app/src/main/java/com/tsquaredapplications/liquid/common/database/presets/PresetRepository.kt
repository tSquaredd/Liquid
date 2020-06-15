package com.tsquaredapplications.liquid.common.database.presets

import androidx.lifecycle.LiveData

interface PresetRepository {
    fun getAllPresets(): LiveData<List<PresetDataWrapper>>
    suspend fun insert(preset: Preset)
    suspend fun delete(preset: Preset)
    suspend fun update(preset: Preset)
}