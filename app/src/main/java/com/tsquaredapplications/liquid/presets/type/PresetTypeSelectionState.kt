package com.tsquaredapplications.liquid.presets.type

import com.tsquaredapplications.liquid.common.database.types.Type
import com.tsquaredapplications.liquid.presets.type.adapter.TypeItem

sealed class PresetTypeSelectionState {
    class Initialized(val typeItems: List<TypeItem>) : PresetTypeSelectionState()
    class TypeSelected(val type: Type) : PresetTypeSelectionState()
}