package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.presets.add.adapter.PresetItem
import javax.inject.Inject

class PresetsViewModel
@Inject constructor(
    private val presetRepository: PresetRepository,
    val userInformation: UserInformation
) : ViewModel() {

    private val state = SingleEventLiveData<PresetState>()
    val stateLiveData: LiveData<PresetState>
        get() = state

    fun getPresets(): LiveData<List<PresetItem>> =
        Transformations.map(presetRepository.getAllPresets()) {
            it.map { presetAndIcon ->
                PresetItem(
                    presetAndIcon,
                    presetAndIcon.preset.createAmountString(userInformation.unitPreference)
                )
            }
        }
}