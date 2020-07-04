package com.tsquaredapplications.liquid.common.database.presets

interface PresetRepository {
    suspend fun getAllPresets(): Map<Int, PresetDataWrapper>
    suspend fun insert(preset: Preset)
    suspend fun delete(preset: Preset)
    suspend fun update(preset: Preset)
}