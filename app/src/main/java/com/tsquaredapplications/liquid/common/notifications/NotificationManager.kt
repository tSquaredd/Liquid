package com.tsquaredapplications.liquid.common.notifications

import com.tsquaredapplications.liquid.common.database.users.UserInformation

interface NotificationManager {
    fun enqueueNotifications(userInformation: UserInformation)
    fun disableNotification()
    fun initialSetup(userInformation: UserInformation)
}