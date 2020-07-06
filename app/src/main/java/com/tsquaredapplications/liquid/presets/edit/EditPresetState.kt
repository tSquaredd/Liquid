package com.tsquaredapplications.liquid.presets.edit

import androidx.annotation.DrawableRes

sealed class EditPresetState {
    class Initialized(
        val name: String,
        @DrawableRes val iconResource: Int,
        val amountText: String,
        val amountUnitHint: String
    ) : EditPresetState()

    class IconUpdated(@DrawableRes val iconResource: Int) : EditPresetState()
    class AmountInvalid(val errorMessage: String) : EditPresetState()
    class NameInvalid(val errorMessage: String) : EditPresetState()
    object Updated : EditPresetState()
    object Deleted : EditPresetState()
}