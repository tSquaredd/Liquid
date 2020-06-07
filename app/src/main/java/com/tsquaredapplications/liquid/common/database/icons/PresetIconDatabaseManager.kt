package com.tsquaredapplications.liquid.common.database.icons

import java.lang.Exception

interface PresetIconDatabaseManager {
    fun getPresetIcons(
        onSuccess: (List<Icon>) -> Unit,
        onFailure: (Exception) -> Unit
    )
}