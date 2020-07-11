package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.presets.Preset
import io.mockk.every
import io.mockk.mockk

fun mockWaterPreset() = mockk<Preset> {
    every { presetUid } returns MOCK_WATER_PRESET_UID
    every { name } returns MOCK_WATER_PRESET_NAME
    every { amount } returns MOCK_WATER_PRESET_AMOUNT
    every { drinkTypeUid } returns MOCK_WATER_DRINK_TYPE_UID
    every { iconUid } returns MOCK_WATER_ICON_UID
}

fun mockBeerPreset() = mockk<Preset> {
    every { presetUid } returns MOCK_BEER_PRESET_UID
    every { name } returns MOCK_BEER_PRESET_NAME
    every { amount } returns MOCK_BEER_PRESET_AMOUNT
    every { drinkTypeUid } returns MOCK_BEER_DRINK_TYPE_UID
    every { iconUid } returns MOCK_BEER_ICON_UID
}

fun mockTeaPreset() = mockk<Preset> {
    every { presetUid } returns MOCK_TEA_PRESET_UID
    every { name } returns MOCK_TEA_PRESET_NAME
    every { amount } returns MOCK_TEA_PRESET_AMOUNT
    every { drinkTypeUid } returns MOCK_TEA_DRINK_TYPE_UID
    every { iconUid } returns MOCK_TEA_ICON_UID
}

const val MOCK_WATER_PRESET_UID = 1
const val MOCK_WATER_PRESET_NAME = "WATER_PRESET"
const val MOCK_WATER_PRESET_AMOUNT = 40.0

const val MOCK_BEER_PRESET_UID = 2
const val MOCK_BEER_PRESET_NAME = "BEER_PRESET"
const val MOCK_BEER_PRESET_AMOUNT = 16.0

const val MOCK_TEA_PRESET_UID = 3
const val MOCK_TEA_PRESET_NAME = "TEA_PRESET"
const val MOCK_TEA_PRESET_AMOUNT = 12.0