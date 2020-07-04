package com.tsquaredapplications.liquid.common.database.icons

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IconDao {
    @Query("SELECT * FROM icon")
    suspend fun getAll(): List<Icon>

    @Query("SELECT * FROM icon WHERE iconUid = :id")
    fun findById(id: Int): LiveData<Icon>

    @Insert
    fun insertAll(iconList: List<Icon>)
}