package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNull

fun mockWaterEntryDataWrapper(asPreset: Boolean = false, customTimestamp: Long? = null) =
    mockk<EntryDataWrapper> {
        every { entry } returns mockWaterEntry(asPreset, customTimestamp)
        every { drinkType } returns mockWaterDrinkType()
        every { icon } returns if (asPreset) mockWaterPresetIcon() else mockWaterIcon()
        every { preset } returns if (asPreset) mockWaterPreset() else null
    }

fun EntryDataWrapper.assertWaterEntryDataWrapper(
    asPreset: Boolean = false,
    customTimestamp: Long? = null
) {
    entry.assertWaterEntry(asPreset, customTimestamp)
    drinkType.assertWaterDrinkType()
    if (asPreset) icon.assertWaterPresetIcon() else icon.assertWaterIcon()
    if (asPreset) preset?.assertWaterPreset() else assertNull(preset)
}

fun mockBeerEntryDataWrapper(asPreset: Boolean = false, customTimestamp: Long? = null) =
    mockk<EntryDataWrapper> {
        every { entry } returns mockBeerEntry(asPreset, customTimestamp)
        every { drinkType } returns mockBeerDrinkType()
        every { icon } returns if (asPreset) mockBeerPresetIcon() else mockBeerIcon()
        every { preset } returns if (asPreset) mockBeerPreset() else null
    }

fun EntryDataWrapper.assertBeerEntryDataWrapper(
    asPreset: Boolean = false,
    customTimestamp: Long? = null
) {
    entry.assertBeerEntry(asPreset, customTimestamp)
    drinkType.assertBeerDrinkType()
    if (asPreset) icon.assertBeerPresetIcon() else icon.assertBeerIcon()
    if (asPreset) preset?.assertBeerPreset() else assertNull(preset)
}

fun mockTeaEntryDataWrapper(asPreset: Boolean = false, customTimestamp: Long? = null) =
    mockk<EntryDataWrapper> {
        every { entry } returns mockTeaEntry(asPreset, customTimestamp)
        every { drinkType } returns mockTeaDrinkType()
        every { icon } returns if (asPreset) mockTeaPresetIcon() else mockTeaIcon()
        every { preset } returns if (asPreset) mockTeaPreset() else null
    }

fun EntryDataWrapper.assertTeaEntryDataWrapper(
    asPreset: Boolean = false,
    customTimestamp: Long? = null
) {
    entry.assertTeaEntry(asPreset, customTimestamp)
    drinkType.assertTeaDrinkType()
    if (asPreset) icon.assertTeaPresetIcon() else icon.assertTeaIcon()
    if (asPreset) preset?.assertTeaPreset() else assertNull(preset)
}