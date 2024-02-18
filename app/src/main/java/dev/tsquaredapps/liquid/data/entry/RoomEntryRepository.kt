package dev.tsquaredapps.liquid.data.entry

import kotlinx.coroutines.flow.Flow

class RoomEntryRepository(
    private val dao: EntryDao
) : EntryRepository {
    override suspend fun addEntry() {
        dao.upsert(Entry(ounces = 1f))
    }

    override fun getAllFlow(): Flow<List<Entry>> = dao.getAllFlow()
}