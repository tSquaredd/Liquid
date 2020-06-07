package com.tsquaredapplications.liquid.presets.icon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.icons.PresetIcon
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.IconSelected
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.icon.adapter.PresetIconItem
import javax.inject.Inject

class PresetIconSelectionViewModel
@Inject constructor(private val presetIcons: List<PresetIcon>) : ViewModel() {

    private val state = SingleEventLiveData<PresetIconSelectionState>()
    val stateLiveData: LiveData<PresetIconSelectionState>
        get() = state

    fun start() {
        val presetIconItems = presetIcons.map { presetIcon ->
            PresetIconItem(presetIcon)

        }
        state.value = Initialized(presetIconItems)
    }

    fun onItemClick(item: PresetIconItem) {
        state.value = IconSelected(item.presetIconModel)
    }
}