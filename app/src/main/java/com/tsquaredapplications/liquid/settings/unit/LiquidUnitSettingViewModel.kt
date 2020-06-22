package com.tsquaredapplications.liquid.settings.unit

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingState.OnUpdated
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingState.UnitSelected
import com.tsquaredapplications.liquid.setup.LiquidUnit
import com.tsquaredapplications.liquid.setup.convertMlToOz
import com.tsquaredapplications.liquid.setup.convertOzToMl
import kotlinx.coroutines.launch
import javax.inject.Inject

class LiquidUnitSettingViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val userManager: UserManager,
    private val entryRepository: EntryRepository
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

        userManager.setUser(userInformation.apply {
            unitPreference = selectedUnit
            if (originalUnit == LiquidUnit.OZ && selectedUnit == LiquidUnit.ML) {
                dailyGoal = convertOzToMl(dailyGoal.toDouble()).toInt()
            } else if (originalUnit == LiquidUnit.ML && selectedUnit == LiquidUnit.OZ) {
                dailyGoal = convertMlToOz(dailyGoal.toDouble()).toInt()
            }
        })

        when {
            (originalUnit == LiquidUnit.OZ && selectedUnit == LiquidUnit.ML) -> {
                viewModelScope.launch {
                    entryRepository.insertAll(
                        entryRepository.getAll().map { it.entry }.apply {
                            forEach { it.amount = convertOzToMl(it.amount) }
                        }
                    )
                }
                onUpdateFinished()
            }
            (originalUnit == LiquidUnit.ML && selectedUnit == LiquidUnit.OZ) -> {
                viewModelScope.launch {
                    entryRepository.insertAll(
                        entryRepository.getAll().map { it.entry }.apply {
                            forEach { it.amount = convertMlToOz(it.amount) }
                        }
                    )
                }
                onUpdateFinished()
            }
            else -> onUpdateFinished()
        }
    }

    private fun onUpdateFinished() {
        state.value = OnUpdated
    }
}