package com.tsquaredapplications.liquid.settings.goal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.Finished
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.Initialized
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.InvalidAmount
import com.tsquaredapplications.liquid.settings.goal.resources.GoalSettingResourceWrapper
import javax.inject.Inject

class GoalSettingViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val userManager: UserManager,
    private val resourceWrapper: GoalSettingResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<GoalSettingState>()
    val stateLiveData: LiveData<GoalSettingState>
        get() = state

    private var goal: Int? = userInformation.dailyGoal

    fun start() {
        state.value = Initialized(
            userInformation.unitPreference.toString(),
            userInformation.dailyGoal.toString()
        )
    }

    fun onGoalInputChanged(text: String) {
        goal = text.toIntOrNull()
    }

    fun update() {
        goal?.let {
            userManager.updateGoal(it)
            state.value = Finished
        } ?: run { state.value = InvalidAmount(resourceWrapper.amountErrorMessage) }
    }
}