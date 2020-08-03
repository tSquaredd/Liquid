package com.tsquaredapplications.liquid.common.database.goal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GoalDao {
    @Query("SELECT * FROM goal ORDER BY startTimeStamp DESC")
    suspend fun getAll(): List<Goal>

    @Insert
    suspend fun insert(goal: Goal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(goals: List<Goal>)
}