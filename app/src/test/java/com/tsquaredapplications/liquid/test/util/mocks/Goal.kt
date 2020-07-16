package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.goal.Goal
import io.mockk.every
import io.mockk.mockk

fun mockGoal(): Goal = mockk {
    every { goalUid } returns GOAL_UID
    every { goalAmount } returns MOCK_DAILY_GOAL
    every { startTimeStamp } returns GOAL_TIMESTAMP
}

const val GOAL_UID = 1
const val GOAL_TIMESTAMP = 12340L