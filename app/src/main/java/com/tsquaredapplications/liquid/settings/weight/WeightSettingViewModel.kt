package com.tsquaredapplications.liquid.settings.weight

import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.convertOzToMl
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.DisableUpdateButton
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.EnabledUpdateButton
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.GoalUpdated
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.Initialized
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.ShowGoalCalculationPrompt
import javax.inject.Inject

class WeightSettingViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val userManager: UserManager,
    private val resourceWrapper: WeightSettingResourceWrapper
) :
    BaseViewModel<WeightSettingState>() {

    private var enteredWeight: Int? = null

    fun start() {
        state.value = Initialized(userInformation.weight)
    }

    fun onWeightUpdated(enteredWeight: String) {
        this.enteredWeight = enteredWeight.toIntOrNull()
        if (this.enteredWeight == null || this.enteredWeight == userInformation.weight) {
            state.value = DisableUpdateButton
        } else {
            state.value = EnabledUpdateButton
        }
    }

    fun onUpdateClicked() {
        userManager.setUser(userInformation.apply {
            weight = enteredWeight!!
        })
        state.value = ShowGoalCalculationPrompt
    }

    fun onCalculateNewGoal() {
        val newGoal = if (userInformation.unitPreference == LiquidUnit.OZ) {
            userInformation.weight / 2
        } else {
            convertOzToMl(userInformation.weight / 2.0)
                .toInt()
        }

        userManager.setUser(userInformation.apply {
            dailyGoal = newGoal
        })

        state.value =
            GoalUpdated("$newGoal ${userInformation.unitPreference}")
    }
}