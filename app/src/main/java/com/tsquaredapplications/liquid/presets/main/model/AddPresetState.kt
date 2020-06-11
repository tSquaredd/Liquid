package com.tsquaredapplications.liquid.presets.main.model

import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.login.LiquidUnit

sealed class AddPresetState {
    class Initialized(val unit: LiquidUnit) : AddPresetState()
    class DrinkTypeSelected(val drinkType: DrinkType) : AddPresetState()
    class PresetIconSelected(val icon: Icon) : AddPresetState()
    class InvalidName(val errorMessage: String): AddPresetState()
    class InvalidDrinkType(val errorMessage: String): AddPresetState()
    object InvalidIcon: AddPresetState()
    class InvalidAmount(val errorMessage: String): AddPresetState()
    class AddPresetSuccess(val preset: Preset): AddPresetState()
    object AddPresetFailed: AddPresetState()
    object ShowProgressBar: AddPresetState()
}