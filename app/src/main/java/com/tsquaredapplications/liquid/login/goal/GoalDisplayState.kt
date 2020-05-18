package com.tsquaredapplications.liquid.login.goal

sealed class GoalDisplayState {
    class Initialized(val goal: String) : GoalDisplayState()
    object DataSuccess : GoalDisplayState()
    class DataFail(val errorMessage: String, val dismissText: String) : GoalDisplayState()
    object ShowProgressBar : GoalDisplayState()
    object HideProgressBar : GoalDisplayState()
}