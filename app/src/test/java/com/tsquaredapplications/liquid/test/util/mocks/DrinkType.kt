package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.types.DrinkType
import io.mockk.every
import io.mockk.mockk

fun mockDrinkTypeWater(): DrinkType = mockk {
    every { drinkTypeUid } returns MOCK_WATER_UID
    every { name } returns MOCK_WATER_NAME
    every { hydration } returns MOCK_WATER_HYDRATION
    every { isAlcohol } returns MOCK_WATER_IS_ALCOHOL
}

const val MOCK_WATER_UID = 1
const val MOCK_WATER_NAME = "Water"
const val MOCK_WATER_HYDRATION = 1.0
const val MOCK_WATER_IS_ALCOHOL = false