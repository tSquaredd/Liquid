package com.tsquaredapplications.liquid.add.drink

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.Initialized
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.adapter.PresetItem
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectDrinkViewModel
@Inject constructor(
    private val typeRepository: TypeRepository,
    private val presetRepository: PresetRepository
) :
    BaseViewModel<SelectDrinkState>() {

    fun start() {
        buildPresetItemsList()
    }

    private fun buildPresetItemsList() {
        viewModelScope.launch {
            val presets = async { presetRepository.getAllPresets().map { PresetItem(it) } }
            val types = async { typeRepository.getAllTypes().map { TypeItem(it) } }
            onItemListsCreated(presets.await(), types.await())
        }
    }

    private fun onItemListsCreated(presets: List<PresetItem>, drinkTypes: List<TypeItem>) {
        state.value = Initialized(presets, drinkTypes)
    }
}