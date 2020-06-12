package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.presets.add.adapter.PresetItem
import com.tsquaredapplications.liquid.presets.main.PresetState.Initialized
import com.tsquaredapplications.liquid.presets.main.PresetState.Refresh
import javax.inject.Inject

class PresetsViewModel
@Inject constructor(
    val presets: List<Preset>,
    val userInformation: UserInformation
) : ViewModel() {

    private val state = SingleEventLiveData<PresetState>()
    val stateLiveData: LiveData<PresetState>
        get() = state

    fun start() {
        state.value = Initialized(presets.map { preset ->
            PresetItem(preset, preset.createAmountString(userInformation.unitPreference))
        })
    }

    fun refresh() {
        state.value = Refresh(presets.map { preset ->
            PresetItem(preset, preset.createAmountString(userInformation.unitPreference))
        })
    }
}