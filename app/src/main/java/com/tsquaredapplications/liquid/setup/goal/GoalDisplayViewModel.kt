package com.tsquaredapplications.liquid.setup.goal

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.calculateDailyGoal
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.common.notifications.NotificationManager
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayState.Initialized
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayState.UserInformationSaved
import javax.inject.Inject

class GoalDisplayViewModel
@Inject constructor(
    private val userManager: UserManager,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val state = SingleEventLiveData<GoalDisplayState>()
    val stateLiveData: LiveData<GoalDisplayState>
        get() = state

    private lateinit var userInformation: UserInformation

    fun start(weight: Int, unit: LiquidUnit) {
        userInformation =
            UserInformation(
                weight = weight,
                unitPreference = unit,
                dailyGoal = calculateDailyGoal(
                    unit,
                    weight
                )
            )

        state.value = Initialized("${userInformation.dailyGoal} ${userInformation.unitPreference}")
    }

    fun onFinishClick() {
        userManager.setUser(userInformation)
        notificationManager.initialSetup(userInformation)
        state.value = UserInformationSaved(userInformation)
    }
}