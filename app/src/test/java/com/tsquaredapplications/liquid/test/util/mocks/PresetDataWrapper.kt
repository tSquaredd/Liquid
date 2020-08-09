package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import io.mockk.every
import io.mockk.mockk

fun mockWaterPresetDataWrapper(): PresetDataWrapper = mockk {
    every { preset } returns mockWaterPreset()
    every { icon } returns mockWaterPresetIcon()
    every { drinkType } returns mockWaterDrinkType()
}

fun PresetDataWrapper.assertWaterPresetDataWrapper() {
    preset.assertWaterPreset()
    icon.assertWaterPresetIcon()
    drinkType.assertWaterDrinkType()
}

fun mockBeerPresetDataWrapper(): PresetDataWrapper = mockk {
    every { preset } returns mockBeerPreset()
    every { icon } returns mockBeerPresetIcon()
    every { drinkType } returns mockBeerDrinkType()
}

fun PresetDataWrapper.assertBeerPresetDataWrapper() {
    preset.assertBeerPreset()
    icon.assertBeerPresetIcon()
    drinkType.assertBeerDrinkType()
}

fun mockTeaPresetDataWrapper(): PresetDataWrapper = mockk {
    every { preset } returns mockTeaPreset()
    every { icon } returns mockTeaPresetIcon()
    every { drinkType } returns mockTeaDrinkType()
}

fun PresetDataWrapper.assertTeaPresetDataWrapper() {
    preset.assertTeaPreset()
    icon.assertTeaPresetIcon()
    drinkType.assertTeaDrinkType()
}