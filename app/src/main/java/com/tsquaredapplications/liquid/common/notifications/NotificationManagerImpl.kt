package com.tsquaredapplications.liquid.common.notifications

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.tsquaredapplications.liquid.common.database.ONBOARDING_NOTIFICATION_SETUP_PERFORMED
import com.tsquaredapplications.liquid.common.database.PREFS_FILE
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationManagerImpl
@Inject constructor(
    private val context: Context
) : NotificationManager {
    override fun enqueueNotifications(userInformation: UserInformation) {
        if (userInformation.notifications.enabled) {
            val notificationRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
                userInformation.notifications.intervalMins.toLong(),
                TimeUnit.MINUTES
            ).setInitialDelay(userInformation.notifications.intervalMins.toLong(), TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    HYDRATION_NOTIFICATION_WORK_NAME,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    notificationRequest
                )
        }
    }

    override fun disableNotification() {
        WorkManager.getInstance(context)
            .cancelUniqueWork(HYDRATION_NOTIFICATION_WORK_NAME)
    }

    override fun initialSetup(userInformation: UserInformation) {
        val sharedPrefs = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        if (sharedPrefs.getBoolean(ONBOARDING_NOTIFICATION_SETUP_PERFORMED, false).not()) {
            enqueueNotifications(userInformation)
            with(sharedPrefs.edit()) {
                putBoolean(ONBOARDING_NOTIFICATION_SETUP_PERFORMED, true)
                commit()
            }
        }
    }

    companion object {
        const val HYDRATION_NOTIFICATION_WORK_NAME = "hydration_notification_work_name"
    }
}