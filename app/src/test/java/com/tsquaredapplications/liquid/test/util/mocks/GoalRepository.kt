package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.goal.GoalRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockGoalRepository(): GoalRepository = mockk {

    coEvery { getAll() } returns listOf(mockGoal())
}