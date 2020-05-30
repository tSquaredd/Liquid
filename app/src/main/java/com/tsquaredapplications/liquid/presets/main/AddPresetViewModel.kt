package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.UserInformation
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState
import javax.inject.Inject

class AddPresetViewModel
@Inject constructor(private val userInformation: UserInformation) : ViewModel() {

    private val state = SingleEventLiveData<AddPresetState>()
    val stateLiveData: LiveData<AddPresetState>
        get() = state

    fun start() {
        state.value = AddPresetState.Initialized(userInformation.unitPreference)
    }
}