package com.tsquaredapplications.liquid.presets.main.model

import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.Type
import com.tsquaredapplications.liquid.login.LiquidUnit

sealed class AddPresetState {
    class Initialized(val unit: LiquidUnit) : AddPresetState()
    class DrinkTypeSelected(val drinkType: Type) : AddPresetState()
    class PresetIconSelected(val icon: Icon) : AddPresetState()
}