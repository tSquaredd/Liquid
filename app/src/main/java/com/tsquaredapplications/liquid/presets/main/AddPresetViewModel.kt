package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.presets.PresetDatabaseManager
import com.tsquaredapplications.liquid.common.database.types.Type
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.AddPresetFailed
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.AddPresetSuccess
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.DrinkTypeSelected
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidAmount
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidIcon
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidName
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.InvalidType
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.PresetIconSelected
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.ShowProgressBar
import com.tsquaredapplications.liquid.presets.main.resources.AddPresetResourceWrapper
import javax.inject.Inject

class AddPresetViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val presetDatabaseManager: PresetDatabaseManager,
    private val resourceWrapper: AddPresetResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<AddPresetState>()
    val stateLiveData: LiveData<AddPresetState>
        get() = state

    private var selectedDrinkType: Type? = null
    private var selectedPresetIcon: Icon? = null
    private var selectedName = ""
    private var selectedAmount = 0.0

    fun start() {
        state.value = AddPresetState.Initialized(userInformation.unitPreference)
    }

    fun drinkTypeSelected(type: Type) {
        selectedDrinkType = type
        state.value = DrinkTypeSelected(type)
    }

    fun presetIconSelected(icon: Icon) {
        selectedPresetIcon = icon
        state.value = PresetIconSelected(icon)
    }

    fun onNameChanged(name: String) {
        selectedName = name
    }

    fun onAmountChanged(amountString: String) {
        val amount = amountString.toDoubleOrNull()
        amount?.let { selectedAmount = it }
    }

    fun onAddClicked() {
        var allValidationsPassed = true

        if (selectedName.isEmpty()) {
            state.value = InvalidName(resourceWrapper.nameErrorMessage)
            allValidationsPassed = false
        }

        if (selectedDrinkType == null) {
            state.value = InvalidType(resourceWrapper.typeErrorMessage)
            allValidationsPassed = false
        }

        if (selectedPresetIcon == null) {
            state.value = InvalidIcon
            allValidationsPassed = false
        }

        if (selectedAmount <= 0) {
            state.value = InvalidAmount(resourceWrapper.sizeErrorMessage)
            allValidationsPassed = false
        }

        if (allValidationsPassed) {
            state.value = ShowProgressBar
            val preset = Preset(
                selectedName,
                selectedAmount,
                selectedDrinkType!!,
                selectedPresetIcon!!
            )

            presetDatabaseManager.addPreset(
                preset,
                onSuccess = {
                    state.value = AddPresetSuccess(preset)
                },
                onFailure = {
                    state.value = AddPresetFailed
                }
            )
        }
    }
}