package com.tsquaredapplications.liquid.common.database.types

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface DrinkTypeDao {
    @Transaction
    @Query("SELECT * FROM drinktype")
    fun getAll(): LiveData<List<DrinkTypeAndIcon>>

    @Insert
    fun insertAll(drinkTypeList: List<DrinkType>)
}