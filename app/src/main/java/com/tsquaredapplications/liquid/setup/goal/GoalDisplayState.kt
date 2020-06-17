package com.tsquaredapplications.liquid.setup.goal

import com.tsquaredapplications.liquid.common.database.users.UserInformation

sealed class GoalDisplayState {
    class Initialized(val goal: String) : GoalDisplayState()
    class UserInformationSaved(val userInformation: UserInformation) : GoalDisplayState()
}