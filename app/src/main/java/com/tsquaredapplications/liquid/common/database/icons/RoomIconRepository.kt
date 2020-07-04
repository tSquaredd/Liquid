package com.tsquaredapplications.liquid.common.database.icons

import javax.inject.Inject

class RoomIconRepository
@Inject constructor(private val iconDao: IconDao) : IconRepository {
    override suspend fun getAllIcons(): List<Icon> = iconDao.getAll()
}