package com.tsquaredapplications.liquid.add.drink

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.Initialized
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.PresetInserted
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.adapter.PresetItem
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.common.database.entry.Entry
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class SelectDrinkViewModel
@Inject constructor(
    private val typeRepository: TypeRepository,
    private val presetRepository: PresetRepository,
    private val entryRepository: EntryRepository
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

    fun presetSelected(presetDataWrapper: PresetDataWrapper) {
        viewModelScope.launch {
            entryRepository.insert(
                Entry(
                    amountInOz = presetDataWrapper.preset.sizeInOz,
                    timestamp = Date().time,
                    drinkTypeUid = presetDataWrapper.drinkType.drinkTypeUid,
                    presetUid = presetDataWrapper.preset.presetUid
                )
            )
            onPresetInserted()
        }
    }

    private fun onPresetInserted() {
        state.value = PresetInserted
    }
}