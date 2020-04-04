package com.tsquaredapplications.liquid

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tsquaredapplications.liquid.model.Entry
import com.tsquaredapplications.liquid.model.EntryDao
import com.tsquaredapplications.liquid.model.EntryDatabase
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntryDatabaseTests {
    private lateinit var dao: EntryDao
    private lateinit var db: EntryDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, EntryDatabase::class.java).build()
        dao = db.entryDao()
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        db.close()
    }

    @Test
    fun testWriteAndRead() {
        val entry = createWaterEntry()
        dao.insert(entry)

        val entries = dao.getAll()
        assertThat(entries.size, equalTo(1))
    }

    @Test
    fun testWriteAndReadThreeEntries() {
        createSameDayWaterEntryList(3).forEach {
            dao.insert(it)
        }

        val entries = dao.getAll()
        assertThat(entries.size, equalTo(3))
    }

    @Test
    fun queryEntriesByDay() {
        val year = 2020
        val month = 2
        val dayOne = 1
        val dayTwo = 2

        createSameDayWaterEntryList(3, month, dayOne).forEach {
            dao.insert(it)
        }

        createSameDayWaterEntryList(4, month, dayTwo).forEach {
            dao.insert(it)
        }

        val dayOneEntries = dao.getAllForDay(year, month, dayOne)
        val dayTwoEntries = dao.getAllForDay(year, month, dayTwo)

        assertThat(dayOneEntries.size, equalTo(3))
        assertThat(dayTwoEntries.size, equalTo(4))
    }

    @Test
    fun updateEntry() {
        dao.insert(Entry(0, "Water", 16.0, 2020, 1, 2, 5, 30))
        var entry = dao.getAll()[0]
        entry = entry.update(hour = 6)
        dao.insert(entry)

        val entries = dao.getAll()
        assertThat(entries.size, equalTo(1))

        val updatedEntry = entries[0]
        assertThat(updatedEntry.hour, equalTo(6))
    }
}