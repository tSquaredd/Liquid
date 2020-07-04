package com.tsquaredapplications.liquid.common.database.icons

import javax.inject.Inject

class RoomIconRepository
@Inject constructor(private val iconDao: IconDao) : IconRepository {
    override suspend fun getAllIcons(): Map<Int, Icon> {
        val icons = iconDao.getAll()
        return icons.map { it.iconUid to it }.toMap()
    }
}