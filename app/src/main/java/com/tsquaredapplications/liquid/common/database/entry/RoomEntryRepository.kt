package com.tsquaredapplications.liquid.common.database.entry

import javax.inject.Inject

class RoomEntryRepository
@Inject constructor(private val entryDao: EntryDao) : EntryRepository {
    override suspend fun getAll(): List<Entry> = entryDao.getAllEntries()

    override suspend fun getAllInTimeRange(from: Long, to: Long): List<Entry> =
        entryDao.getAllForTimeRange(from, to)

    override suspend fun insert(entry: Entry) {
        entryDao.insert(entry)
    }
}