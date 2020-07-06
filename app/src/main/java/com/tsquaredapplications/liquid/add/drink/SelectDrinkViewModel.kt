package com.tsquaredapplications.liquid.add.drink

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.Initialized
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.PresetInserted
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.adapter.PresetItem
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.common.database.entry.Entry
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeAndIcon
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class SelectDrinkViewModel
@Inject constructor(
    private val typeRepository: TypeRepository,
    private val presetRepository: PresetRepository,
    private val entryRepository: EntryRepository,
    private val userManager: UserManager,
    private val userInformation: UserInformation,
    private val resourceWrapper: SelectDrinkResourceWrapper
) :
    BaseViewModel<SelectDrinkState>() {

    fun start() {
        buildPresetItemsList()
    }

    private fun buildPresetItemsList() {
        viewModelScope.launch {
            val presets = async { presetRepository.getAllPresets().values.map { PresetItem(it) } }
            val types =
                async { typeRepository.getAllDrinkTypesWithIcons().values.map { TypeItem(it) } }
            onItemListsCreated(presets.await(), types.await())
        }
    }

    private fun onItemListsCreated(presets: List<PresetItem>, drinkTypes: List<TypeItem>) {
        state.value = Initialized(presets, drinkTypes)
    }

    fun presetSelected(presetDataWrapper: PresetDataWrapper) {
        viewModelScope.launch {
            entryRepository.insert(
                Entry(
                    amount = presetDataWrapper.preset.amount,
                    timestamp = Date().time,
                    drinkTypeUid = presetDataWrapper.drinkType.drinkTypeUid,
                    presetUid = presetDataWrapper.preset.presetUid
                )
            )
            onPresetInserted(presetDataWrapper.drinkType.isAlcohol)
        }
    }

    private fun onPresetInserted(isAlcohol: Boolean) {
        state.value = PresetInserted(
            userManager.shouldShowAlcoholWarning() && isAlcohol,
            resourceWrapper.getWarningCalculations(userInformation.unitPreference),
            resourceWrapper.getSuggestion(userInformation.unitPreference)
        )
    }

    fun onDrinkTypeSelected(drinkTypeAndIcon: DrinkTypeAndIcon) {
        state.value = SelectDrinkState.DrinkTypeSelected(
            drinkTypeAndIcon = drinkTypeAndIcon,
            showAlcoholWarning = userManager.shouldShowAlcoholWarning() && drinkTypeAndIcon.drinkType.isAlcohol,
            alcoholCalculations = resourceWrapper.getWarningCalculations(userInformation.unitPreference),
            alcoholSuggestion = resourceWrapper.getSuggestion(userInformation.unitPreference)
        )
    }

    fun alcoholWarningDismissed(dontShowAlcoholWarningAgain: Boolean) {
        if (dontShowAlcoholWarningAgain) {
            userManager.setDonNotShowAlcoholWarning()
        }
    }
}