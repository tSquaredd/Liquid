package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.Entry
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals

fun mockWaterEntry(asPreset: Boolean = false, customTimestamp: Long? = null): Entry = mockk {
    every { entryUid } returns WATER_ENTRY_UID
    every { amount } returns if (asPreset) MOCK_WATER_PRESET_AMOUNT else WATER_ENTRY_AMOUNT
    every { timestamp } returns (customTimestamp ?: WATER_ENTRY_TIMESTAMP)
    every { drinkTypeUid } returns MOCK_WATER_DRINK_TYPE_UID
    every { presetUid } returns if (asPreset) MOCK_WATER_PRESET_UID else null
}

fun Entry.assertWaterEntry(asPreset: Boolean = false, customTimestamp: Long? = null) {
    assertEquals(WATER_ENTRY_UID, entryUid)
    assertEquals(if (asPreset) MOCK_WATER_PRESET_AMOUNT else WATER_ENTRY_AMOUNT, amount)
    assertEquals((customTimestamp ?: WATER_ENTRY_TIMESTAMP), timestamp)
    assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(if (asPreset) MOCK_WATER_PRESET_UID else null, presetUid)
}

fun mockBeerEntry(asPreset: Boolean = false, customTimestamp: Long? = null): Entry = mockk {
    every { entryUid } returns BEER_ENTRY_UID
    every { amount } returns if (asPreset) MOCK_BEER_PRESET_AMOUNT else BEER_ENTRY_AMOUNT
    every { timestamp } returns (customTimestamp ?: BEER_ENTRY_TIMESTAMP)
    every { drinkTypeUid } returns MOCK_BEER_DRINK_TYPE_UID
    every { presetUid } returns if (asPreset) MOCK_BEER_PRESET_UID else null
}

fun Entry.assertBeerEntry(asPreset: Boolean = false, customTimestamp: Long? = null) {
    assertEquals(BEER_ENTRY_UID, entryUid)
    assertEquals(if (asPreset) MOCK_BEER_PRESET_AMOUNT else BEER_ENTRY_AMOUNT, amount)
    assertEquals((customTimestamp ?: BEER_ENTRY_TIMESTAMP), timestamp)
    assertEquals(MOCK_BEER_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(if (asPreset) MOCK_BEER_PRESET_UID else null, presetUid)
}

fun mockTeaEntry(asPreset: Boolean = false, customTimestamp: Long? = null): Entry = mockk {
    every { entryUid } returns TEA_ENTRY_UID
    every { amount } returns if (asPreset) MOCK_TEA_PRESET_AMOUNT else TEA_ENTRY_AMOUNT
    every { timestamp } returns (customTimestamp ?: TEA_ENTRY_TIMESTAMP)
    every { drinkTypeUid } returns MOCK_TEA_DRINK_TYPE_UID
    every { presetUid } returns if (asPreset) MOCK_TEA_PRESET_UID else null
}

fun Entry.assertTeaEntry(asPreset: Boolean = false, customTimestamp: Long? = null) {
    assertEquals(TEA_ENTRY_UID, entryUid)
    assertEquals(if (asPreset) MOCK_TEA_PRESET_AMOUNT else TEA_ENTRY_AMOUNT, amount)
    assertEquals((customTimestamp ?: TEA_ENTRY_TIMESTAMP), timestamp)
    assertEquals(MOCK_TEA_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(if (asPreset) MOCK_TEA_PRESET_UID else null, presetUid)
}

const val WATER_ENTRY_UID = 1
const val WATER_ENTRY_AMOUNT = 16.0
const val WATER_ENTRY_TIMESTAMP = 12345L

const val BEER_ENTRY_UID = 2
const val BEER_ENTRY_AMOUNT = 12.0
const val BEER_ENTRY_TIMESTAMP = 12347L

const val TEA_ENTRY_UID = 1
const val TEA_ENTRY_AMOUNT = 8.0
const val TEA_ENTRY_TIMESTAMP = 12349L