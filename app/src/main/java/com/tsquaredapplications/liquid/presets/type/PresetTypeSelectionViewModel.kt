package com.tsquaredapplications.liquid.presets.type

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.TypeSelected
import com.tsquaredapplications.liquid.presets.type.adapter.TypeItem
import javax.inject.Inject

class PresetTypeSelectionViewModel
@Inject constructor(private val drinkTypes: List<DrinkType>) : ViewModel() {

    private val state = SingleEventLiveData<PresetTypeSelectionState>()
    val stateLiveData: LiveData<PresetTypeSelectionState>
        get() = state

    fun start() {
        val typeItems = drinkTypes.map { type ->
            TypeItem(type)
        }.sortedBy { it.drinkType.name }

        state.value = Initialized(typeItems)
    }

    fun onItemClick(item: TypeItem) {
        state.value = TypeSelected(item.drinkType)
    }
}