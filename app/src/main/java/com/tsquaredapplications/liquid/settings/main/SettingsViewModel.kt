package com.tsquaredapplications.liquid.settings.main

import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.settings.resources.SettingsResourceWrapper
import javax.inject.Inject

class SettingsViewModel
@Inject constructor(
    private val userManager: UserManager,
    private val resourceWrapper: SettingsResourceWrapper
) : ViewModel() {

    fun getSettingsItems() = listOf(
        SettingsItem(
            Setting(
                name = resourceWrapper.dailyGoal,
                value = "${userManager.getUser().dailyGoal}${userManager.getUser().unitPreference}",
                settingType = SettingType.DailyGoal
            )
        ),
        SettingsItem(
            Setting(
                name = resourceWrapper.weight,
                value = "${userManager.getUser().weight} ${resourceWrapper.lbs}",
                settingType = SettingType.Weight
            )
        ),
        SettingsItem(
            Setting(
                name = resourceWrapper.preferredMeasurement,
                value = "${userManager.getUser().unitPreference}",
                settingType = SettingType.UnitPreference
            )
        ),
        SettingsItem(
            Setting(
                name = resourceWrapper.allowReminders,
                value = if (userManager.getUser().notifications.enabled) resourceWrapper.on else resourceWrapper.off,
                settingType = SettingType.Notifications
            )
        ),
        SettingsItem(
            Setting(
                name = resourceWrapper.rateThisApp,
                value = "",
                settingType = SettingType.RateThisApp
            )
        )
    )
}