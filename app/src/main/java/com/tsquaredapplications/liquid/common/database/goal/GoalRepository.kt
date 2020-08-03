package com.tsquaredapplications.liquid.common.database.goal

interface GoalRepository {
    suspend fun getAll(): List<Goal>
    suspend fun insert(goal: Goal)
    suspend fun insertAll(goals: List<Goal>)
}