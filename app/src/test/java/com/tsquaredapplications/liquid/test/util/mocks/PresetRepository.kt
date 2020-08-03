package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockPresetRepository(withPresets: Boolean): PresetRepository = mockk(relaxUnitFun = true) {
    val presets = mutableMapOf<Int, PresetDataWrapper>().apply {
        if (withPresets) {
            put(MOCK_WATER_PRESET_UID, mockWaterPresetDataWrapper())
            put(MOCK_BEER_PRESET_UID, mockBeerPresetDataWrapper())
            put(MOCK_TEA_PRESET_UID, mockTeaPresetDataWrapper())
        }
    }

    coEvery { getAllPresets() } returns presets
}