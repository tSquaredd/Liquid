package com.tsquaredapplications.liquid.presets.type

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.TypeSelected
import kotlinx.coroutines.launch
import javax.inject.Inject

class PresetTypeSelectionViewModel
@Inject constructor(private val typeRepository: TypeRepository) :
    BaseViewModel<PresetTypeSelectionState>() {

    fun start() {
        viewModelScope.launch {
            val typeItems = typeRepository.getAllTypes().map {
                TypeItem(it)
            }

            state.value = Initialized(typeItems)
        }
    }

    fun onItemClick(item: TypeItem) {
        state.value = TypeSelected(item.drinkTypeAndIcon.drinkType)
    }
}