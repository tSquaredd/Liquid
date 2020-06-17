package com.tsquaredapplications.liquid.presets.add.model

import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.setup.LiquidUnit

sealed class AddPresetState {
    class Initialized(val unit: LiquidUnit) : AddPresetState()
    class DrinkTypeSelected(val drinkType: DrinkType) : AddPresetState()
    class PresetIconSelected(val icon: Icon) : AddPresetState()
    class InvalidName(val errorMessage: String) : AddPresetState()
    class InvalidDrinkType(val errorMessage: String) : AddPresetState()
    object InvalidIcon : AddPresetState()
    class InvalidAmount(val errorMessage: String) : AddPresetState()
    object PresetAdded : AddPresetState()
}