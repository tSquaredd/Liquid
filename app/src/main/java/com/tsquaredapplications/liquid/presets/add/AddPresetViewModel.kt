package com.tsquaredapplications.liquid.presets.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.DrinkTypeSelected
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidAmount
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidDrinkType
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidIcon
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidName
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.PresetIconSelected
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPresetViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val presetRepository: PresetRepository,
    private val resourceWrapper: AddPresetResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<AddPresetState>()
    val stateLiveData: LiveData<AddPresetState>
        get() = state

    private var selectedDrinkType: DrinkType? = null
    private var selectedPresetIcon: Icon? = null
    private var selectedName = ""
    private var selectedAmount: Double? = null

    fun start() {
        state.value = AddPresetState.Initialized(userInformation.unitPreference)
    }

    fun drinkTypeSelected(drinkType: DrinkType) {
        selectedDrinkType = drinkType
        state.value = DrinkTypeSelected(drinkType)
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
            state.value = InvalidDrinkType(resourceWrapper.typeErrorMessage)
            allValidationsPassed = false
        }

        if (selectedPresetIcon == null) {
            state.value = InvalidIcon
            allValidationsPassed = false
        }

        if (selectedAmount == null || selectedAmount!! <= 0) {
            state.value = InvalidAmount(resourceWrapper.amountErrorMessage)
            allValidationsPassed = false
        }

        if (allValidationsPassed) {
            viewModelScope.launch {
                presetRepository.insert(
                    Preset(
                        name = selectedName,
                        sizeInOz = selectedAmount!!,
                        drinkTypeUid = selectedDrinkType!!.drinkTypeUid,
                        iconUid = selectedPresetIcon!!.iconUid
                    )
                )
                insertFinished()
            }
        }
    }

    private fun insertFinished() {
        state.value = AddPresetState.PresetAdded
    }
}