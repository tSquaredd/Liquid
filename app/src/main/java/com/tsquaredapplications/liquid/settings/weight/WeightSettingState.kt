package com.tsquaredapplications.liquid.settings.weight

sealed class WeightSettingState {
    class Initialized(val weight: Int) : WeightSettingState()
    object EnabledUpdateButton : WeightSettingState()
    object DisableUpdateButton : WeightSettingState()
    object ShowGoalCalculationPrompt : WeightSettingState()
    class GoalUpdated(val goal: String) : WeightSettingState()
}