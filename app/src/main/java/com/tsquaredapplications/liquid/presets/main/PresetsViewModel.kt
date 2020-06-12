package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.presets.main.PresetState.Initialized
import com.tsquaredapplications.liquid.presets.main.PresetState.Refresh
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.presets.add.adapter.PresetItem
import javax.inject.Inject

class PresetsViewModel
@Inject constructor(val presets: List<Preset>) : ViewModel() {

    private val state = SingleEventLiveData<PresetState>()
    val stateLiveData: LiveData<PresetState>
        get() = state

    fun start() {
        state.value = Initialized(presets.map {
            PresetItem(
                it
            )
        })
    }

    fun refresh() {
        state.value = Refresh(presets.map {
            PresetItem(
                it
            )
        })
    }
}