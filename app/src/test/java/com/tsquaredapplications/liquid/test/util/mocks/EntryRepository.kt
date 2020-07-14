package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockEntryRepository(withEntriesForTimeRange: Boolean = true): EntryRepository = mockk {
    coEvery { insert(any()) } returns Unit

    val entryList =
        listOf(mockWaterEntryDataWrapper(), mockBeerEntryDataWrapper(), mockTeaEntryDataWrapper())

    coEvery {
        getAllInTimeRange(
            any(),
            any()
        )
    } returns if (withEntriesForTimeRange) entryList else emptyList()

    coEvery { delete(any()) } returns Unit

    coEvery { update(any()) } returns Unit
}