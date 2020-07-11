package com.tsquaredapplications.liquid.settings.unit

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.convertMlToOz
import com.tsquaredapplications.liquid.common.convertOzToMl
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingState.OnUpdated
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingState.UnitSelected
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class LiquidUnitSettingViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val userManager: UserManager,
    private val entryRepository: EntryRepository,
    private val presetRepository: PresetRepository
) :
    BaseViewModel<LiquidUnitSettingState>() {

    private var selectedUnit = userInformation.unitPreference

    fun start() {
        state.value = LiquidUnitSettingState.Initialized(selectedUnit)
    }

    fun onUnitSelected(liquidUnit: LiquidUnit) {
        selectedUnit = liquidUnit
        state.value = UnitSelected(liquidUnit)
    }

    fun onUpdateClicked() {
        val originalUnit = userInformation.unitPreference

        updateUserInformation(originalUnit)
        viewModelScope.launch {
            val deferredEntryUpdate = async { updateEntries(originalUnit) }
            val deferredPresetUpdate = async { updatePresets(originalUnit) }
            deferredEntryUpdate.await()
            deferredPresetUpdate.await()
            onUpdateFinished()
        }
    }

    private fun updateUserInformation(originalUnit: LiquidUnit) {
        userManager.setUser(userInformation.apply {
            unitPreference = selectedUnit
            if (originalUnit == LiquidUnit.OZ && selectedUnit == LiquidUnit.ML) {
                dailyGoal = convertOzToMl(
                    dailyGoal.toDouble()
                ).toInt()
            } else if (originalUnit == LiquidUnit.ML && selectedUnit == LiquidUnit.OZ) {
                dailyGoal = convertMlToOz(
                    dailyGoal.toDouble()
                ).toInt()
            }
        })
    }

    private suspend fun updateEntries(originalUnit: LiquidUnit) {
        when {
            (originalUnit == LiquidUnit.OZ && selectedUnit == LiquidUnit.ML) -> {
                entryRepository.insertAll(
                    entryRepository.getAll().map { it.entry }.apply {
                        forEach {
                            it.amount =
                                convertOzToMl(
                                    it.amount
                                )
                        }
                    }
                )
            }
            (originalUnit == LiquidUnit.ML && selectedUnit == LiquidUnit.OZ) -> {
                entryRepository.insertAll(
                    entryRepository.getAll().map { it.entry }.apply {
                        forEach {
                            it.amount =
                                convertMlToOz(
                                    it.amount
                                )
                        }
                    }
                )
            }
        }
    }

    private suspend fun updatePresets(originalUnit: LiquidUnit) {
        when {
            (originalUnit == LiquidUnit.OZ && selectedUnit == LiquidUnit.ML) -> {
                presetRepository.insertAll(
                    presetRepository.getAllPresets().map { it.value.preset }.apply {
                        forEach {
                            it.amount =
                                convertOzToMl(
                                    it.amount
                                )
                        }
                    }
                )
            }
            (originalUnit == LiquidUnit.ML && selectedUnit == LiquidUnit.OZ) -> {
                presetRepository.insertAll(
                    presetRepository.getAllPresets().map { it.value.preset }.apply {
                        forEach {
                            it.amount =
                                convertMlToOz(
                                    it.amount
                                )
                        }
                    }
                )
            }
        }
    }

    private fun onUpdateFinished() {
        state.value = OnUpdated
    }
}