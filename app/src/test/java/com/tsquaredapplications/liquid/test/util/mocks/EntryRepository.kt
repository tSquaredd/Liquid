package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockEntryRepository(
    withEntries: Boolean = true,
    withAlcoholEntries: Boolean = true,
    withNonAlcoholEntries: Boolean = true,
    allEntriesList: List<EntryDataWrapper> = emptyList()
): EntryRepository = mockk {
    coEvery { insert(any()) } returns Unit

    val timeRangeEntryList = mutableListOf<EntryDataWrapper>().apply {
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
    } returns timeRangeEntryList

    coEvery { getAll() } returns allEntriesList
    coEvery { delete(any()) } returns Unit
    coEvery { update(any()) } returns Unit
    coEvery { presetRemoval(any()) } returns Unit
}