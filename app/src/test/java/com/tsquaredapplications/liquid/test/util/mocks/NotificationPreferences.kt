package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.users.NotificationsPreferences
import io.mockk.every
import io.mockk.mockk

fun mockNotificationPreferences(): NotificationsPreferences = mockk {
    every { enabled } returns MOCK_ENABLED
    every { startTime } returns mockk {
        every { hour } returns MOCK_NOTIFICATION_START_TIME_HOUR
        every { min } returns MOCK_NOTIFICATION_START_TIME_MINUTE
    }
    every { endTime } returns mockk {
        every { hour } returns MOCK_NOTIFICATION_END_TIME_HOUR
        every { min } returns MOCK_NOTIFICATION_END_TIME_MINUTE
    }
    every { intervalMins } returns MOCK_INTERVAL_MINUTES

}

const val MOCK_ENABLED = true
const val MOCK_NOTIFICATION_START_TIME_HOUR = 9
const val MOCK_NOTIFICATION_START_TIME_MINUTE = 0
const val MOCK_NOTIFICATION_END_TIME_HOUR = 21
const val MOCK_NOTIFICATION_END_TIME_MINUTE = 0
const val MOCK_INTERVAL_MINUTES = 120