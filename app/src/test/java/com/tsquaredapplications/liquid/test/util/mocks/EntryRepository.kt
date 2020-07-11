package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockEntryRepository(numEntries: Int): EntryRepository = mockk {
    val entriesList = mutableListOf<EntryDataWrapper>().apply {
        repeat(numEntries) { add(mockEntryDataWrapper()) }
    }

    coEvery { getAll() } returns entriesList

    coEvery { insert(any()) } returns Unit
}