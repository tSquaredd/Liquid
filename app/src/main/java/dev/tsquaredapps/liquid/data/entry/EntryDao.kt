package dev.tsquaredapps.liquid.data.entry

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Upsert
    fun upsert(entry: Entry)

    @Query("select * from entry")
    fun getAllFlow(): Flow<List<Entry>>
}