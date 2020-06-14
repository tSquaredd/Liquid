package com.tsquaredapplications.liquid.common.database.types

import androidx.lifecycle.LiveData
import javax.inject.Inject

class RoomTypeRepository
@Inject constructor(private val drinkTypeDao: DrinkTypeDao) : TypeRepository {
    override fun getAllTypes(): LiveData<List<DrinkTypeAndIcon>> = drinkTypeDao.getAll()
}