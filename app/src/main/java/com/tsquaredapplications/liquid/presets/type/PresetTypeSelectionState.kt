package com.tsquaredapplications.liquid.presets.type

import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.presets.type.adapter.TypeItem

sealed class PresetTypeSelectionState {
    class Initialized(val typeItems: List<TypeItem>) : PresetTypeSelectionState()
    class TypeSelected(val drinkType: DrinkType) : PresetTypeSelectionState()
}