package com.tsquaredapplications.liquid.common.database.types

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface DrinkTypeDao {
    @Transaction
    @Query("SELECT * FROM drinktype")
    suspend fun getAll(): List<DrinkTypeAndIcon>

    @Insert
    fun insertAll(drinkTypeList: List<DrinkType>)
}