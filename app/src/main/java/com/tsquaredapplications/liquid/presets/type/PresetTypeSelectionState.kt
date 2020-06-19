package com.tsquaredapplications.liquid.presets.type

import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.common.database.types.DrinkType

sealed class PresetTypeSelectionState {
    class Initialized(val typeItems: List<TypeItem>) : PresetTypeSelectionState()
    class TypeSelected(val drinkType: DrinkType) : PresetTypeSelectionState()
}