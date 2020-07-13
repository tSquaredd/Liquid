package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import io.mockk.every
import io.mockk.mockk

fun mockEntryDataWrapper(): EntryDataWrapper = mockk()

fun mockWaterEntryDataWrapper() = mockk<EntryDataWrapper> {
    every { entry } returns mockWaterEntry()
    every { drinkType } returns mockWaterDrinkType()
    every { icon } returns mockWaterIcon()
}

fun mockBeerEntryDataWrapper() = mockk<EntryDataWrapper> {
    every { entry } returns mockBeerEntry()
    every { drinkType } returns mockBeerDrinkType()
    every { icon } returns mockBeerIcon()
}

fun mockTeaEntryDataWrapper() = mockk<EntryDataWrapper> {
    every { entry } returns mockTeaEntry()
    every { drinkType } returns mockTeaDrinkType()
    every { icon } returns mockTeaIcon()
}