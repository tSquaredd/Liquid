package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.model.Entry
import java.util.concurrent.ThreadLocalRandom

fun createWaterEntry() = Entry(0, "Water", 16.0, 2020, 1, 1, 5, 30)

fun createSameDayWaterEntryList(numEntries: Int) = createSameDayWaterEntryList(numEntries, 1, 1)

fun createSameDayWaterEntryList(numEntries: Int, month: Int, day: Int): List<Entry> {
    val year = 2020
    var hour = 1
    val minute = 30

    val list = mutableListOf<Entry>()
    repeat(numEntries) {
        hour++

        if (hour < 23) {
            list.add(
                Entry(
                    0,
                    "Water",
                    ThreadLocalRandom.current().nextDouble(4.0, 50.0),
                    year,
                    month,
                    day,
                    hour,
                    minute
                )
            )
        }

    }
    return list
}