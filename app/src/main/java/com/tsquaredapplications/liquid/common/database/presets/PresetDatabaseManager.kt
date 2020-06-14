package com.tsquaredapplications.liquid.common.database.presets

import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType

interface PresetDatabaseManager {
    fun addPreset(
        name: String,
        sizeInOz: Double,
        drinkType: DrinkType,
        icon: Icon,
        onSuccess: (Preset) -> Unit,
        onFailure: (Exception?) -> Unit
    )

    fun getAllPresets(
        onSuccess: (List<Preset>) -> Unit,
        onFailure: (Exception?) -> Unit
    )

    fun delete(
        preset: Preset,
        onSuccess: (Preset) -> Unit,
        onFailure: (Exception?) -> Unit
    )

    fun update(
        preset: Preset,
        onSuccess: (Preset) -> Unit,
        onFailure: (Exception?) -> Unit
    )
}