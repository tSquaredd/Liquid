package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.entry.Entry
import io.mockk.every
import io.mockk.mockk

fun mockWaterEntry(asPreset: Boolean = false): Entry = mockk {
    every { entryUid } returns WATER_ENTRY_UID
    every { amount } returns if (asPreset) MOCK_WATER_PRESET_AMOUNT else WATER_ENTRY_AMOUNT
    every { timestamp } returns WATER_ENTRY_TIMESTAMP
    every { drinkTypeUid } returns MOCK_WATER_DRINK_TYPE_UID
    every { presetUid } returns if (asPreset) MOCK_WATER_PRESET_UID else null
}

fun mockBeerEntry(asPreset: Boolean = false): Entry = mockk {
    every { entryUid } returns BEER_ENTRY_UID
    every { amount } returns if (asPreset) MOCK_BEER_PRESET_AMOUNT else BEER_ENTRY_AMOUNT
    every { timestamp } returns BEER_ENTRY_TIMESTAMP
    every { drinkTypeUid } returns MOCK_BEER_DRINK_TYPE_UID
    every { presetUid } returns if (asPreset) MOCK_BEER_PRESET_UID else null
}

fun mockTeaEntry(asPreset: Boolean = false): Entry = mockk {
    every { entryUid } returns TEA_ENTRY_UID
    every { amount } returns if (asPreset) MOCK_TEA_PRESET_AMOUNT else TEA_ENTRY_AMOUNT
    every { timestamp } returns TEA_ENTRY_TIMESTAMP
    every { drinkTypeUid } returns MOCK_TEA_DRINK_TYPE_UID
    every { presetUid } returns if (asPreset) MOCK_TEA_PRESET_UID else null
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