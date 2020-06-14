package com.tsquaredapplications.liquid.presets.edit

import com.tsquaredapplications.liquid.common.database.presets.Preset

sealed class EditPresetState {
    class Initialized(
        val name: String,
        val iconPath: String,
        val amountText: String,
        val amountUnitHint: String
    ) : EditPresetState()

    class IconUpdated(val iconPath: String) : EditPresetState()
    class AmountInvalid(val errorMessage: String) : EditPresetState()
    class NameInvalid(val errorMessage: String) : EditPresetState()
    class UpdateSuccess(val updatedPreset: Preset) : EditPresetState()
    class UpdatedFailure(val failureMessage: String, val dismissText: String) : EditPresetState()
    class DeleteSuccess(val deletedPreset: Preset) : EditPresetState()
    class DeleteFailure(val failureMessage: String, val dismissText: String) : EditPresetState()
    object ShowProgressBar : EditPresetState()
    object HideProgressBar : EditPresetState()
}