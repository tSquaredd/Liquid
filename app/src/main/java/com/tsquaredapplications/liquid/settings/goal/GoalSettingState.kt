package com.tsquaredapplications.liquid.settings.goal

sealed class GoalSettingState {
    class Initialized(val unitPreference: String, val currentGoal: String) : GoalSettingState()
    class InvalidAmount(val errorMessage: String) : GoalSettingState()
    object Finished : GoalSettingState()
}