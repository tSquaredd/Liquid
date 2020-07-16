package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.presets.Preset
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals

fun mockWaterPreset() = mockk<Preset> {
    every { presetUid } returns MOCK_WATER_PRESET_UID
    every { name } returns MOCK_WATER_PRESET_NAME
    every { amount } returns MOCK_WATER_PRESET_AMOUNT
    every { drinkTypeUid } returns MOCK_WATER_DRINK_TYPE_UID
    every { iconUid } returns MOCK_WATER_PRESET_ICON_UID
}

fun Preset.assertWaterPreset() {
    assertEquals(MOCK_WATER_PRESET_UID, presetUid)
    assertEquals(MOCK_WATER_PRESET_NAME, name)
    assertEquals(MOCK_WATER_PRESET_AMOUNT, amount)
    assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(MOCK_WATER_PRESET_ICON_UID, iconUid)
}

fun mockBeerPreset() = mockk<Preset> {
    every { presetUid } returns MOCK_BEER_PRESET_UID
    every { name } returns MOCK_BEER_PRESET_NAME
    every { amount } returns MOCK_BEER_PRESET_AMOUNT
    every { drinkTypeUid } returns MOCK_BEER_DRINK_TYPE_UID
    every { iconUid } returns MOCK_BEER_PRESET_ICON_UID
}

fun Preset.assertBeerPreset() {
    assertEquals(MOCK_BEER_PRESET_UID, presetUid)
    assertEquals(MOCK_BEER_PRESET_NAME, name)
    assertEquals(MOCK_BEER_PRESET_AMOUNT, amount)
    assertEquals(MOCK_BEER_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(MOCK_BEER_PRESET_ICON_UID, iconUid)
}

fun mockTeaPreset() = mockk<Preset> {
    every { presetUid } returns MOCK_TEA_PRESET_UID
    every { name } returns MOCK_TEA_PRESET_NAME
    every { amount } returns MOCK_TEA_PRESET_AMOUNT
    every { drinkTypeUid } returns MOCK_TEA_DRINK_TYPE_UID
    every { iconUid } returns MOCK_TEA_PRESET_ICON_UID
}

fun Preset.assertTeaPreset() {
    assertEquals(MOCK_TEA_PRESET_UID, presetUid)
    assertEquals(MOCK_TEA_PRESET_NAME, name)
    assertEquals(MOCK_TEA_PRESET_AMOUNT, amount)
    assertEquals(MOCK_TEA_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(MOCK_TEA_PRESET_ICON_UID, iconUid)
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