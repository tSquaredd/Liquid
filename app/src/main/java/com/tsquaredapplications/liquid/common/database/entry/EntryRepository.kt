package com.tsquaredapplications.liquid.common.database.entry

interface EntryRepository {
    suspend fun getAll(): List<Entry>
    suspend fun getAllInTimeRange(from: Long, to: Long): List<Entry>
    suspend fun insert(entry: Entry)
}