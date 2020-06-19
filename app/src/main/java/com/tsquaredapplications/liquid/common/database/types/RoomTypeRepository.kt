package com.tsquaredapplications.liquid.common.database.types

import javax.inject.Inject

class RoomTypeRepository
@Inject constructor(private val drinkTypeDao: DrinkTypeDao) : TypeRepository {
    override suspend fun getAllTypes(): List<DrinkTypeAndIcon> = drinkTypeDao.getAll()
}