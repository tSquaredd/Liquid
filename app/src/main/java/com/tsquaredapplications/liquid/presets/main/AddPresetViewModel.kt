package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.Type
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.DrinkTypeSelected
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.PresetIconSelected
import javax.inject.Inject

class AddPresetViewModel
@Inject constructor(private val userInformation: UserInformation) : ViewModel() {

    private val state = SingleEventLiveData<AddPresetState>()
    val stateLiveData: LiveData<AddPresetState>
        get() = state

    private var selectedDrinkType: Type? = null
    private var selectedPresetIcon: Icon? = null

    fun start() {
        state.value = AddPresetState.Initialized(userInformation.unitPreference)
    }

    fun drinkTypeSelected(selectedDrinkType: Type) {
        this.selectedDrinkType = selectedDrinkType
        state.value = DrinkTypeSelected(selectedDrinkType)
    }

    fun presetIconSelected(selectedPresetIcon: Icon) {
        this.selectedPresetIcon = selectedPresetIcon
        state.value = PresetIconSelected(selectedPresetIcon)
    }
}