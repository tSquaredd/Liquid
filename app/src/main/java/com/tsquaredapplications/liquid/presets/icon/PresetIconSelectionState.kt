package com.tsquaredapplications.liquid.presets.icon

import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.presets.icon.adapter.PresetIconItem

sealed class PresetIconSelectionState {
    class Initialized(val typeItems: List<PresetIconItem>) : PresetIconSelectionState()
    class IconSelected(val icon: Icon) : PresetIconSelectionState()
}