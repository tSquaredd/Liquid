package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import io.mockk.every
import io.mockk.mockk

fun mockUserInformation(): UserInformation = mockk(relaxUnitFun = true) {
    every { weight } returns MOCK_WEIGHT
    every { unitPreference } returns MOCK_UNIT_PREF
    every { dailyGoal } returns MOCK_DAILY_GOAL
    every { notifications } returns mockNotificationPreferences()
}

const val MOCK_WEIGHT = 190
val MOCK_UNIT_PREF = LiquidUnit.OZ
const val MOCK_DAILY_GOAL = 95
