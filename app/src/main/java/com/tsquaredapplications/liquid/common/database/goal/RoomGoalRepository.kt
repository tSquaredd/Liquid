package com.tsquaredapplications.liquid.common.database.goal

import javax.inject.Inject

class RoomGoalRepository
@Inject constructor(private val goalDao: GoalDao) : GoalRepository {
    override suspend fun getAll(): List<Goal> =
        goalDao.getAll()

    override suspend fun insert(goal: Goal) {
        val latestGoal = goalDao.getAll().firstOrNull()
        latestGoal?.let {
            if (it.goalAmount == goal.goalAmount) {
                return
            }
        }
        goalDao.insert(goal)
    }

    override suspend fun insertAll(goals: List<Goal>) {
        goalDao.insertAll(goals)
    }
}