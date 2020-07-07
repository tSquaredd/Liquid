package com.tsquaredapplications.liquid.presets.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.AmountInvalid
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Deleted
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.IconUpdated
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Initialized
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Updated
import com.tsquaredapplications.liquid.presets.edit.resources.EditPresetResourceWrapper
import com.tsquaredapplications.liquid.setup.LiquidUnit
import com.tsquaredapplications.liquid.setup.convertMlToOz
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditPresetViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val presetRepository: PresetRepository,
    private val entryRepository: EntryRepository,
    private val resourceWrapper: EditPresetResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<EditPresetState>()
    val stateLiveData: LiveData<EditPresetState>
        get() = state

    lateinit var preset: Preset
    private lateinit var originalPreset: Preset
    private var updatedAmount: Double? = null
    private var updatedName: String? = null
    private var updatedIcon: Icon? = null

    fun start(presetDataWrapper: PresetDataWrapper) {
        this.preset = presetDataWrapper.preset
        originalPreset = presetDataWrapper.preset.copy()
        updatedAmount = preset.amount
        updatedName = preset.name
        updatedIcon = presetDataWrapper.icon
        state.value = Initialized(
            preset.name,
            presetDataWrapper.icon.largeIconResource,
            preset.createAmountString(userInformation.unitPreference),
            userInformation.unitPreference.toString()
        )
    }

    fun deletePreset() {
        viewModelScope.launch {
            val deferredDeletion = async { presetRepository.delete(originalPreset) }
            val entryUpdates = async { entryRepository }
            awaitAll(deferredDeletion, entryUpdates)
            state.value = Deleted
        }
    }

    fun presetIconSelected(icon: Icon) {
        this.updatedIcon = icon
        state.value = IconUpdated(icon.largeIconResource)
    }

    fun onAmountChanged(amountString: String) {
        var amountDouble = amountString.toDoubleOrNull()
        if (amountDouble != null && userInformation.unitPreference == LiquidUnit.ML) {
            amountDouble = convertMlToOz(amountDouble)
        }
        updatedAmount = amountDouble
    }

    fun onNameChanged(name: String) {
        this.updatedName = name
    }

    fun updatePreset() {
        var allValidationsPassed = true

        if (updatedAmount == null) {
            state.value = AmountInvalid(resourceWrapper.amountErrorMessage)
            allValidationsPassed = false
        }

        if (updatedName.isNullOrEmpty()) {
            state.value = EditPresetState.NameInvalid(resourceWrapper.nameErrorMessage)
            allValidationsPassed = false
        }

        if (allValidationsPassed) {
            viewModelScope.launch {
                presetRepository.update(preset.apply {
                    name = updatedName!!
                    amount = updatedAmount!!
                    iconUid = updatedIcon?.iconUid ?: originalPreset.iconUid
                })
            }
            state.value = Updated
        }
    }
}