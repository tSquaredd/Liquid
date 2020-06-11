package com.tsquaredapplications.liquid.presets.icon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.IconSelected
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.icon.adapter.PresetIconItem
import javax.inject.Inject

class PresetIconSelectionViewModel
@Inject constructor(
    private val presetIcons: List<Icon>,
    private val drinkTypes: List<DrinkType>
) : ViewModel() {

    private val state = SingleEventLiveData<PresetIconSelectionState>()
    val stateLiveData: LiveData<PresetIconSelectionState>
        get() = state

    fun start() {
        val presetIconItems = presetIcons.map { presetIcon ->
            PresetIconItem(presetIcon)
        }.toMutableList()

        drinkTypes.forEach { type ->
            presetIconItems.add(PresetIconItem(type.icon))
        }

        state.value = Initialized(presetIconItems)
    }

    fun onItemClick(item: PresetIconItem) {
        state.value = IconSelected(item.iconModel)
    }
}