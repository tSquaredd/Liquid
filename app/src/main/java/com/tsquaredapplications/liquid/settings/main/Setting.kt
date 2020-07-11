package com.tsquaredapplications.liquid.settings.main

class Setting(val name: String, val value: String, val settingType: SettingType)

enum class SettingType {
    DailyGoal,
    Weight,
    UnitPreference,
    Notifications,
    RateThisApp
}