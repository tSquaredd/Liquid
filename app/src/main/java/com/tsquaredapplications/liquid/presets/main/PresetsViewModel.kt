package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.presets.add.adapter.DetailedPresetItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class PresetsViewModel
@Inject constructor(
    private val presetRepository: PresetRepository,
    val userInformation: UserInformation
) : ViewModel() {

    private val state = SingleEventLiveData<PresetState>()
    val stateLiveData: LiveData<PresetState>
        get() = state

    fun getPresets() {
        viewModelScope.launch {
            val presets = presetRepository.getAllPresets()
            onPresetsRetrieved(presets.values.toList())
        }
    }

    private fun onPresetsRetrieved(presets: List<PresetDataWrapper>) {
        state.value = PresetState.Initialized(presets.map {
            DetailedPresetItem(it, it.preset.createAmountString(userInformation.unitPreference))
        })
    }
}