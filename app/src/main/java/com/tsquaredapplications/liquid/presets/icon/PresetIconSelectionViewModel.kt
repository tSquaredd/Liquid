package com.tsquaredapplications.liquid.presets.icon

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.IconSelected
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.icon.adapter.PresetIconItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class PresetIconSelectionViewModel
@Inject constructor(
    private val iconRepository: IconRepository
) : BaseViewModel<PresetIconSelectionState>() {

    fun getTypes() {
        viewModelScope.launch {
            state.value = Initialized(
                iconRepository.getAllIcons().map {
                    PresetIconItem(it)
                })
        }
    }

    fun onItemClick(item: PresetIconItem) {
        state.value = IconSelected(item.iconModel)
    }
}

sealed class PresetIconSelectionState {
    class Initialized(val typeItems: List<PresetIconItem>) : PresetIconSelectionState()
    class IconSelected(val icon: Icon) : PresetIconSelectionState()
}