package com.tsquaredapplications.liquid.common.database.icons

import androidx.lifecycle.LiveData
import javax.inject.Inject

class RoomIconRepository
@Inject constructor(private val iconDao: IconDao) : IconRepository {
    override fun getAllIcons(): LiveData<List<Icon>> = iconDao.getAll()
}