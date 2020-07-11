package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockEntryRepository(): EntryRepository = mockk {
    coEvery { insert(any()) } returns Unit
}