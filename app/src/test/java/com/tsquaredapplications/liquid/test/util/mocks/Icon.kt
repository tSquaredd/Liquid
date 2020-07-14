package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.icons.Icon
import io.mockk.every
import io.mockk.mockk

fun mockWaterIcon(): Icon = mockk {
    every { iconUid } returns MOCK_WATER_ICON_UID
    every { iconResource } returns MOCK_WATER_ICON_RESOURCE
    every { largeIconResource } returns MOCK_WATER_LARGE_ICON_RESOURCE
}

fun mockWaterPresetIcon(): Icon = mockk {
    every { iconUid } returns MOCK_WATER_PRESET_ICON_UID
    every { iconResource } returns MOCK_WATER_PRESET_ICON_RESOURCE
    every { largeIconResource } returns MOCK_WATER_PRESET_LARGE_ICON_RESOURCE
}

fun mockBeerIcon(): Icon = mockk {
    every { iconUid } returns MOCK_BEER_ICON_UID
    every { iconResource } returns MOCK_BEER_ICON_RESOURCE
    every { largeIconResource } returns MOCK_BEER_LARGE_ICON_RESOURCE
}

fun mockBeerPresetIcon(): Icon = mockk {
    every { iconUid } returns MOCK_BEER_PRESET_ICON_UID
    every { iconResource } returns MOCK_BEER_PRESET_ICON_RESOURCE
    every { largeIconResource } returns MOCK_BEER_PRESET_LARGE_ICON_RESOURCE
}

fun mockTeaIcon(): Icon = mockk {
    every { iconUid } returns MOCK_TEA_ICON_UID
    every { iconResource } returns MOCK_TEA_ICON_RESOURCE
    every { largeIconResource } returns MOCK_TEA_LARGE_ICON_RESOURCE
}

fun mockTeaPresetIcon(): Icon = mockk {
    every { iconUid } returns MOCK_TEA_PRESET_ICON_UID
    every { iconResource } returns MOCK_TEA_PRESET_ICON_RESOURCE
    every { largeIconResource } returns MOCK_TEA_PRESET_LARGE_ICON_RESOURCE
}

const val MOCK_WATER_ICON_UID = 1
const val MOCK_WATER_ICON_RESOURCE = 100
const val MOCK_WATER_LARGE_ICON_RESOURCE = 1000

const val MOCK_BEER_ICON_UID = 2
const val MOCK_BEER_ICON_RESOURCE = 200
const val MOCK_BEER_LARGE_ICON_RESOURCE = 2000

const val MOCK_TEA_ICON_UID = 3
const val MOCK_TEA_ICON_RESOURCE = 300
const val MOCK_TEA_LARGE_ICON_RESOURCE = 3000

const val MOCK_WATER_PRESET_ICON_UID = 4
const val MOCK_WATER_PRESET_ICON_RESOURCE = 101
const val MOCK_WATER_PRESET_LARGE_ICON_RESOURCE = 1001

const val MOCK_BEER_PRESET_ICON_UID = 5
const val MOCK_BEER_PRESET_ICON_RESOURCE = 201
const val MOCK_BEER_PRESET_LARGE_ICON_RESOURCE = 2001

const val MOCK_TEA_PRESET_ICON_UID = 6
const val MOCK_TEA_PRESET_ICON_RESOURCE = 301
const val MOCK_TEA_PRESET_LARGE_ICON_RESOURCE = 3001