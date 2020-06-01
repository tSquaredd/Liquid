package com.tsquaredapplications.liquid.presets.type

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.types.Type
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.TypeSelected
import com.tsquaredapplications.liquid.presets.type.adapter.TypeItem
import javax.inject.Inject

class PresetTypeSelectionViewModel
@Inject constructor(private val types: List<Type>) : ViewModel() {

    private val state = SingleEventLiveData<PresetTypeSelectionState>()
    val stateLiveData: LiveData<PresetTypeSelectionState>
        get() = state

    fun start() {
        val typeItems = types.map { type ->
            TypeItem(type)
        }.sortedBy { it.typeModel.name }

        state.value = Initialized(typeItems)
    }

    fun onItemClick(item: TypeItem) {
        state.value = TypeSelected(item.typeModel)
    }
}