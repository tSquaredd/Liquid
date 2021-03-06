package com.tsquaredapplications.liquid.add.drink

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.Initialized
import com.tsquaredapplications.liquid.add.drink.SelectDrinkState.PresetInserted
import com.tsquaredapplications.liquid.add.drink.resources.SelectDrinkResourceWrapper
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

    private val presets = mutableListOf<PresetItem>()
    private val types = mutableListOf<TypeItem>()

    fun start() {
        buildDrinkList()
    }

    private fun buildDrinkList() {
        if (types.isEmpty()) {
            viewModelScope.launch {
                val deferredPresets =
                    async { presetRepository.getAllPresets().values.map { PresetItem(it) } }
                val deferredTypes =
                    async { typeRepository.getAllDrinkTypesWithIcons().values.map { TypeItem(it) } }

                presets.addAll(deferredPresets.await())
                types.addAll(deferredTypes.await())
                onItemListsCreated(presets, types)
            }
        } else {
            onItemListsCreated(presets, types)
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
            isAlcohol && userManager.shouldShowAlcoholWarning(),
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

sealed class SelectDrinkState {
    class Initialized(val presets: List<PresetItem>, val drinkTypes: List<TypeItem>) :
        SelectDrinkState()

    class PresetInserted(
        val showAlcoholWarning: Boolean,
        val alcoholCalculations: String,
        val alcoholSuggestion: String
    ) : SelectDrinkState()

    class DrinkTypeSelected(
        val drinkTypeAndIcon: DrinkTypeAndIcon,
        val showAlcoholWarning: Boolean,
        val alcoholCalculations: String,
        val alcoholSuggestion: String
    ) : SelectDrinkState()
}