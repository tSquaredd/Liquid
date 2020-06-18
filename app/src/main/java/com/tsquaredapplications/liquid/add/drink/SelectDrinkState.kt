package com.tsquaredapplications.liquid.add.drink

import com.tsquaredapplications.liquid.common.adapter.PresetItem
import com.tsquaredapplications.liquid.common.adapter.TypeItem

sealed class SelectDrinkState {
    class Initialized(val presets: List<PresetItem>, val drinkTypes: List<TypeItem>) :
        SelectDrinkState()
}