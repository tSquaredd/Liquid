package com.tsquaredapplications.liquid.add.drink

import com.tsquaredapplications.liquid.common.adapter.PresetItem
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeAndIcon

sealed class SelectDrinkState {
    class Initialized(val presets: List<PresetItem>, val drinkTypes: List<TypeItem>) :
        SelectDrinkState()

    class PresetInserted(
        val showAlcoholWarning: Boolean,
        val alcoholCalculations: String,
        val alcoholSuggestion: String
    ) : SelectDrinkState()

    class DrinkTypeSelected(
        val drinkTypeAndIcon: DrinkTypeAndIcon,
        val showAlcoholWarning: Boolean,
        val alcoholCalculations: String,
        val alcoholSuggestion: String
    ) : SelectDrinkState()
}