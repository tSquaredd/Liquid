package com.tsquaredapplications.liquid.login.goal

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.users.UserDatabaseManager
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.login.LiquidUnit
import com.tsquaredapplications.liquid.login.calculateDailyGoal
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.DataFail
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.DataSuccess
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.HideProgressBar
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.Initialized
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.ShowProgressBar
import com.tsquaredapplications.liquid.login.goal.resources.GoalDisplayResourceWrapper
import javax.inject.Inject

class GoalDisplayViewModel
@Inject constructor(
    private val userDatabaseManager: UserDatabaseManager,
    private val resourceWrapper: GoalDisplayResourceWrapper
) :
    ViewModel() {

    private val state = SingleEventLiveData<GoalDisplayState>()
    val stateLiveData: LiveData<GoalDisplayState>
        get() = state

    private lateinit var userInformation: UserInformation

    fun start(weight: Int, unit: LiquidUnit) {
        userInformation =
            UserInformation(
                weight = weight,
                unitPreference = unit,
                dailyGoal = calculateDailyGoal(unit, weight)
            )

        state.value = Initialized("${userInformation.dailyGoal} ${userInformation.unitPreference}")
    }

    fun onFinishClick() {
        state.value = ShowProgressBar
        userDatabaseManager.setUser(
            userInformation,
            onCompletion = ::onSetUserComplete,
            onFail = ::onSetUserFail,
            onSuccess = ::onSetUserSuccess
        )
    }

    @VisibleForTesting
    fun onSetUserComplete() {
        state.value = HideProgressBar
    }

    @VisibleForTesting
    fun onSetUserFail() {
        state.value =
            DataFail(resourceWrapper.errorMessage, resourceWrapper.dismissErrorText)
    }

    @VisibleForTesting
    fun onSetUserSuccess(userInformation: UserInformation) {
        state.value = DataSuccess(userInformation)
    }
}