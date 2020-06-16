package com.tsquaredapplications.liquid.common.database.users

import android.content.Context
import com.tsquaredapplications.liquid.common.database.DAILY_GOAL
import com.tsquaredapplications.liquid.common.database.NOTIFICATIONS
import com.tsquaredapplications.liquid.common.database.PREFS_FILE
import com.tsquaredapplications.liquid.common.database.UNIT_PREFERENCE
import com.tsquaredapplications.liquid.common.database.WEIGHT
import com.tsquaredapplications.liquid.setup.LiquidUnit
import javax.inject.Inject

class UserManagerImpl
@Inject constructor(val context: Context) :
    UserManager {
    override fun setUser(userInformation: UserInformation) {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putInt(WEIGHT, userInformation.weight)
            putInt(DAILY_GOAL, userInformation.dailyGoal)
            putInt(UNIT_PREFERENCE, if (userInformation.unitPreference == LiquidUnit.OZ) 0 else 1)
            putInt(NOTIFICATIONS, if (userInformation.notifications) 0 else 1)
            commit()
        }
    }

    override fun getUser(): UserInformation? {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val weight = sharedPrefs.getInt(WEIGHT, -1)
        if (weight == -1) return null

        val dailyGoal = sharedPrefs.getInt(DAILY_GOAL, -1)
        if (dailyGoal == -1) return null

        val unitPreferenceInt = sharedPrefs.getInt(UNIT_PREFERENCE, -1)
        if (unitPreferenceInt == -1) return null
        val unitPreference = if (unitPreferenceInt == 0) LiquidUnit.OZ else LiquidUnit.ML

        val notificationsInt = sharedPrefs.getInt(NOTIFICATIONS, -1)
        if (notificationsInt == -1) return null
        val notifications = notificationsInt == 0

        return UserInformation(weight, unitPreference, dailyGoal, notifications)
    }
}