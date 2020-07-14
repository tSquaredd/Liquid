package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import io.mockk.every
import io.mockk.mockk

fun mockEntryDataWrapper(): EntryDataWrapper = mockk()

fun mockWaterEntryDataWrapper(asPreset: Boolean = false) = mockk<EntryDataWrapper> {
    every { entry } returns mockWaterEntry(asPreset)
    every { drinkType } returns mockWaterDrinkType()
    every { icon } returns if (asPreset) mockWaterPresetIcon() else mockWaterIcon()
    every { preset } returns if (asPreset) mockWaterPreset() else null
}

fun mockBeerEntryDataWrapper(asPreset: Boolean = false) = mockk<EntryDataWrapper> {
    every { entry } returns mockBeerEntry(asPreset)
    every { drinkType } returns mockBeerDrinkType()
    every { icon } returns if (asPreset) mockBeerPresetIcon() else mockBeerIcon()
    every { preset } returns if (asPreset) mockBeerPreset() else null
}

fun mockTeaEntryDataWrapper(asPreset: Boolean = false) = mockk<EntryDataWrapper> {
    every { entry } returns mockTeaEntry(asPreset)
    every { drinkType } returns mockTeaDrinkType()
    every { icon } returns if (asPreset) mockTeaPresetIcon() else mockTeaIcon()
    every { preset } returns if (asPreset) mockTeaPreset() else null
}