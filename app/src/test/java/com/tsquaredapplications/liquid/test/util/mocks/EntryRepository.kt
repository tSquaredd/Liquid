package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockEntryRepository(
    withEntries: Boolean = true,
    withAlcoholEntries: Boolean = true,
    withNonAlcoholEntries: Boolean = true
): EntryRepository = mockk {
    coEvery { insert(any()) } returns Unit

    val entryList = mutableListOf<EntryDataWrapper>().apply {
        if (withEntries && withAlcoholEntries) add(mockBeerEntryDataWrapper())
        if (withEntries && withNonAlcoholEntries) {
            add(mockWaterEntryDataWrapper())
            add(mockTeaEntryDataWrapper())
        }
    }

    coEvery {
        getAllInTimeRange(
            any(),
            any()
        )
    } returns entryList

    coEvery { delete(any()) } returns Unit

    coEvery { update(any()) } returns Unit
}