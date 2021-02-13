package com.tsquaredapplications.liquid.common.database.users

import android.content.Context
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.database.DAILY_GOAL
import com.tsquaredapplications.liquid.common.database.NOTIFICATIONS_ENABLED
import com.tsquaredapplications.liquid.common.database.NOTIFICATION_END_TIME_HOUR
import com.tsquaredapplications.liquid.common.database.NOTIFICATION_END_TME_MIN
import com.tsquaredapplications.liquid.common.database.NOTIFICATION_INTERVAL_MINS
import com.tsquaredapplications.liquid.common.database.NOTIFICATION_START_TIME_HOUR
import com.tsquaredapplications.liquid.common.database.NOTIFICATION_START_TIME_MIN
import com.tsquaredapplications.liquid.common.database.PREFS_FILE
import com.tsquaredapplications.liquid.common.database.SHOULD_SHOW_ALCOHOL_WARNING
import com.tsquaredapplications.liquid.common.database.UNIT_PREFERENCE
import com.tsquaredapplications.liquid.common.database.WEIGHT
import com.tsquaredapplications.liquid.common.database.goal.Goal
import com.tsquaredapplications.liquid.common.database.goal.GoalRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class UserManagerImpl
@Inject constructor(
    @ApplicationContext val context: Context,
    private val goalRepository: GoalRepository
) :
    UserManager {
    override fun setUser(userInformation: UserInformation) {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putInt(WEIGHT, userInformation.weight)
            putInt(DAILY_GOAL, userInformation.dailyGoal)
            putInt(UNIT_PREFERENCE, if (userInformation.unitPreference == LiquidUnit.OZ) 0 else 1)
            putInt(NOTIFICATIONS_ENABLED, if (userInformation.notifications.enabled) 0 else 1)
            putInt(NOTIFICATION_START_TIME_HOUR, userInformation.notifications.startTime.hour)
            putInt(NOTIFICATION_START_TIME_MIN, userInformation.notifications.startTime.min)
            putInt(NOTIFICATION_END_TIME_HOUR, userInformation.notifications.endTime.hour)
            putInt(NOTIFICATION_END_TME_MIN, userInformation.notifications.endTime.min)
            putInt(NOTIFICATION_INTERVAL_MINS, userInformation.notifications.intervalMins)
            commit()
        }

        GlobalScope.launch {
            goalRepository.insert(
                Goal(
                    goalAmount = userInformation.dailyGoal,
                    startTimeStamp = Calendar.getInstance().timeInMillis
                )
            )
        }
    }

    override fun getUser(): UserInformation {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        val weight = sharedPrefs.getInt(WEIGHT, -1)

        val dailyGoal = sharedPrefs.getInt(DAILY_GOAL, -1)

        val unitPreferenceInt = sharedPrefs.getInt(UNIT_PREFERENCE, -1)
        val unitPreference = if (unitPreferenceInt == 0) LiquidUnit.OZ else LiquidUnit.ML

        val notificationsInt = sharedPrefs.getInt(NOTIFICATIONS_ENABLED, -1)
        val notificationsEnabled = notificationsInt == 0

        val notificationStartTime = NotificationTime(
            sharedPrefs.getInt(NOTIFICATION_START_TIME_HOUR, 9),
            sharedPrefs.getInt(NOTIFICATION_START_TIME_MIN, 0)
        )

        val notificationEndTime = NotificationTime(
            sharedPrefs.getInt(NOTIFICATION_END_TIME_HOUR, 21),
            sharedPrefs.getInt(NOTIFICATION_END_TME_MIN, 0)
        )

        val notificationInterval = sharedPrefs.getInt(NOTIFICATION_INTERVAL_MINS, 120)
        return UserInformation(
            weight,
            unitPreference,
            dailyGoal,
            NotificationsPreferences(
                notificationsEnabled,
                notificationStartTime,
                notificationEndTime,
                notificationInterval
            )
        )
    }

    override fun shouldShowAlcoholWarning(): Boolean {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(SHOULD_SHOW_ALCOHOL_WARNING, true)
    }

    override fun setDonNotShowAlcoholWarning() {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putBoolean(SHOULD_SHOW_ALCOHOL_WARNING, false)
            commit()
        }
    }

    override fun updateGoal(goal: Int) {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putInt(DAILY_GOAL, goal)
            commit()
        }

        GlobalScope.launch {
            goalRepository.insert(
                Goal(
                    goalAmount = goal,
                    startTimeStamp = Calendar.getInstance().timeInMillis
                )
            )
        }
    }

    override fun isUserSet(): Boolean {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        return sharedPrefs.getInt(WEIGHT, -1) != -1
    }
}