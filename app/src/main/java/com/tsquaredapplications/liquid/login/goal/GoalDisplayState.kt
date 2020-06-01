package com.tsquaredapplications.liquid.login.goal

import com.tsquaredapplications.liquid.common.database.users.UserInformation

sealed class GoalDisplayState {
    class Initialized(val goal: String) : GoalDisplayState()
    class DataSuccess(val userInformation: UserInformation) : GoalDisplayState()
    class DataFail(val errorMessage: String, val dismissText: String) : GoalDisplayState()
    object ShowProgressBar : GoalDisplayState()
    object HideProgressBar : GoalDisplayState()
}