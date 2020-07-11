package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import io.mockk.every
import io.mockk.mockk

fun mockWaterPresetDataWrapper(): PresetDataWrapper = mockk {
    every { preset } returns mockWaterPreset()
    every { icon } returns mockWaterIcon()
    every { drinkType } returns mockWaterDrinkType()
}

fun mockBeerPresetDataWrapper(): PresetDataWrapper = mockk {
    every { preset } returns mockBeerPreset()
    every { icon } returns mockBeerIcon()
    every { drinkType } returns mockBeerDrinkType()
}

fun mockTeaPresetDataWrapper(): PresetDataWrapper = mockk {
    every { preset } returns mockTeaPreset()
    every { icon } returns mockTeaIcon()
    every { drinkType } returns mockTeaDrinkType()
}