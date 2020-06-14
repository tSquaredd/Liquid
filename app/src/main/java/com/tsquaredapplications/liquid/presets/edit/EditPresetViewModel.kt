package com.tsquaredapplications.liquid.presets.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.presets.PresetDatabaseManager
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.login.LiquidUnit
import com.tsquaredapplications.liquid.login.convertMlToOz
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.AmountInvalid
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.DeleteFailure
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.DeleteSuccess
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.HideProgressBar
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.IconUpdated
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Initialized
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.ShowProgressBar
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.UpdateSuccess
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.UpdatedFailure
import com.tsquaredapplications.liquid.presets.edit.resources.EditPresetResourceWrapper
import javax.inject.Inject

class EditPresetViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val presetDatabaseManager: PresetDatabaseManager,
    private val resourceWrapper: EditPresetResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<EditPresetState>()
    val stateLiveData: LiveData<EditPresetState>
        get() = state

    lateinit var preset: Preset
    lateinit var originalPreset: Preset
    private var updatedAmountInOz: Double? = null
    private var updatedName: String? = null
    private var updatedIcon: Icon? = null

    fun start(preset: Preset) {
        this.preset = preset
        originalPreset = preset.copy()
        updatedAmountInOz = preset.sizeInOz
        updatedName = preset.name
        updatedIcon = preset.icon
        state.value = Initialized(
            preset.name,
            preset.icon.largeIconPath,
            preset.createAmountString(userInformation.unitPreference),
            userInformation.unitPreference.toString()
        )
    }

    fun deletePreset() {
        presetDatabaseManager.delete(preset,
            onSuccess = {
                state.value = DeleteSuccess(it)
            },
            onFailure = {
                state.value = HideProgressBar
                state.value = DeleteFailure(
                    resourceWrapper.deleteFailureMessage,
                    resourceWrapper.failureDismissText
                )
                start(originalPreset)
            })
    }

    fun presetIconSelected(icon: Icon) {
        this.updatedIcon = icon
        state.value = IconUpdated(icon.largeIconPath)
    }

    fun onAmountChanged(amountString: String) {
        var amountDouble = amountString.toDoubleOrNull()
        if (amountDouble != null && userInformation.unitPreference == LiquidUnit.ML) {
            amountDouble = convertMlToOz(amountDouble)
        }
        updatedAmountInOz = amountDouble
    }

    fun onNameChanged(name: String) {
        this.updatedName = name
    }

    fun updatePreset() {
        var allValidationsPassed = true

        if (updatedAmountInOz == null) {
            state.value = AmountInvalid(resourceWrapper.amountErrorMessage)
            allValidationsPassed = false
        }

        if (updatedName.isNullOrEmpty()) {
            state.value = EditPresetState.NameInvalid(resourceWrapper.nameErrorMessage)
            allValidationsPassed = false
        }

        if (allValidationsPassed) {
            state.value = ShowProgressBar
            presetDatabaseManager.update(
                preset = preset.apply {
                    icon = updatedIcon!!
                    name = updatedName!!
                    sizeInOz = updatedAmountInOz!!
                },
                onSuccess = {
                    state.value = HideProgressBar
                    state.value = UpdateSuccess(it)
                },
                onFailure = {
                    state.value = HideProgressBar
                    state.value = UpdatedFailure(
                        resourceWrapper.updateFailureMessage,
                        resourceWrapper.failureDismissText
                    )
                    start(originalPreset)
                })
        }
    }
}