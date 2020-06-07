package com.tsquaredapplications.liquid.common.database.presets

interface PresetDatabaseManager {
    fun addPreset(
        preset: Preset,
        onSuccess: () -> Unit,
        onFailure: (Exception?) -> Unit
    )

    fun getAllPresets(
        onSuccess: (List<Preset>) -> Unit,
        onFailure: (Exception?) -> Unit
    )
}