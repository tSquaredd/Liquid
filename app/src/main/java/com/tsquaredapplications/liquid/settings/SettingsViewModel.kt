package com.tsquaredapplications.liquid.settings

import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.settings.resources.SettingsResourceWrapper
import java.util.*
import javax.inject.Inject

class SettingsViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val resourceWrapper: SettingsResourceWrapper
) : ViewModel() {

    fun getSettingsItems() = listOf(
        SettingsItem(
            Setting(
                name = resourceWrapper.dailyGoal,
                value = "${userInformation.dailyGoal}${userInformation.unitPreference.name.toLowerCase(
                    Locale.getDefault()
                )}",
                settingType = SettingType.DailyGoal
            )
        ),
        SettingsItem(
            Setting(
                name = resourceWrapper.weight,
                value = "${userInformation.weight} ${resourceWrapper.lbs}",
                settingType = SettingType.Weight
            )
        ),
        SettingsItem(
            Setting(
                name = resourceWrapper.preferredMeasurement,
                value = "${userInformation.unitPreference}",
                settingType = SettingType.UnitPreference
            )
        ),
        SettingsItem(
            Setting(
                name = resourceWrapper.allowReminders,
                value = if (userInformation.notifications.enabled) resourceWrapper.on else resourceWrapper.off,
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