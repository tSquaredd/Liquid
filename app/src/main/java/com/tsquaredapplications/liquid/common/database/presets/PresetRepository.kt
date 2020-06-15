package com.tsquaredapplications.liquid.common.database.presets

interface PresetRepository {
    suspend fun getAllPresets(): List<PresetDataWrapper>
    suspend fun insert(preset: Preset)
    suspend fun delete(preset: Preset)
    suspend fun update(preset: Preset)
}