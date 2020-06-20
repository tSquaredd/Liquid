package com.tsquaredapplications.liquid.common.database.entry

interface EntryRepository {
    suspend fun getAll(): List<EntryDataWrapper>
    suspend fun getAllInTimeRange(from: Long, to: Long): List<EntryDataWrapper>
    suspend fun insert(entry: Entry)
}