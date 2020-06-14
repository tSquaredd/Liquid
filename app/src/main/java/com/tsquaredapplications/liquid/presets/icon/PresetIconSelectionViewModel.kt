package com.tsquaredapplications.liquid.presets.icon

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.IconSelected
import com.tsquaredapplications.liquid.presets.icon.adapter.PresetIconItem
import javax.inject.Inject

class PresetIconSelectionViewModel
@Inject constructor(
    private val iconRepository: IconRepository
) : ViewModel() {

    private val state = SingleEventLiveData<PresetIconSelectionState>()
    val stateLiveData: LiveData<PresetIconSelectionState>
        get() = state

    fun getTypes(): LiveData<List<PresetIconItem>> {
        return Transformations.map(iconRepository.getAllIcons()) {
            it.map { icon ->
                PresetIconItem(icon)
            }
        }
    }

    fun onItemClick(item: PresetIconItem) {
        state.value = IconSelected(item.iconModel)
    }
}