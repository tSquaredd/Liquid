package dev.tsquaredapps.liquid.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tsquaredapps.liquid.data.entry.Entry
import dev.tsquaredapps.liquid.data.entry.EntryDao

@Database(entities = [Entry::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun entryDao(): EntryDao

    companion object {
        const val DB_NAME = "liquid-db"
    }
}