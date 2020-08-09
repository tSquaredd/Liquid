package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.users.NotificationTime
import com.tsquaredapplications.liquid.common.database.users.NotificationsPreferences
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals

fun mockNotificationPreferences(withCopy: Boolean = true): NotificationsPreferences = mockk() {
    every { enabled } returns MOCK_ENABLED
    every { startTime } returns mockk(relaxUnitFun = true) {
        every { hour } returns MOCK_NOTIFICATION_START_TIME_HOUR
        every { min } returns MOCK_NOTIFICATION_START_TIME_MINUTE
    }
    every { endTime } returns mockk(relaxUnitFun = true) {
        every { hour } returns MOCK_NOTIFICATION_END_TIME_HOUR
        every { min } returns MOCK_NOTIFICATION_END_TIME_MINUTE
    }
    every { intervalMins } returns MOCK_INTERVAL_MINUTES

    if (withCopy) every { copy() } returns mockNotificationPreferences(false)
}

fun NotificationsPreferences.assertNotificationPreferences() {
    assertEquals(MOCK_ENABLED, this)
    startTime.assertStartTime()
    endTime.assertEndTime()
    assertEquals(MOCK_INTERVAL_MINUTES, intervalMins)
}

fun NotificationTime.assertStartTime() {
    assertEquals(MOCK_NOTIFICATION_START_TIME_HOUR, hour)
    assertEquals(MOCK_NOTIFICATION_START_TIME_MINUTE, min)
}

fun NotificationTime.assertEndTime() {
    assertEquals(MOCK_NOTIFICATION_END_TIME_HOUR, hour)
    assertEquals(MOCK_NOTIFICATION_END_TIME_MINUTE, min)
}

const val MOCK_ENABLED = true
const val MOCK_NOTIFICATION_START_TIME_HOUR = 9
const val MOCK_NOTIFICATION_START_TIME_MINUTE = 0
const val MOCK_NOTIFICATION_END_TIME_HOUR = 21
const val MOCK_NOTIFICATION_END_TIME_MINUTE = 0
const val MOCK_INTERVAL_MINUTES = 120