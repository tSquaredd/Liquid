package dev.tsquaredapps.liquid.data.entry

import kotlinx.coroutines.flow.Flow

interface EntryRepository {
    suspend fun addEntry()

    fun getAllFlow(): Flow<List<Entry>>
}