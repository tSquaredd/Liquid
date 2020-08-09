package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.types.DrinkType
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals

fun mockWaterDrinkType(): DrinkType = mockk {
    every { drinkTypeUid } returns MOCK_WATER_DRINK_TYPE_UID
    every { name } returns MOCK_WATER_DRINK_TYPE_NAME
    every { hydration } returns MOCK_WATER_DRINK_TYPE_HYDRATION
    every { isAlcohol } returns MOCK_WATER_DRINK_TYPE_IS_ALCOHOL
}

fun DrinkType.assertWaterDrinkType() {
    assertEquals(MOCK_WATER_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(MOCK_WATER_DRINK_TYPE_NAME, name)
    assertEquals(MOCK_WATER_DRINK_TYPE_HYDRATION, hydration)
    assertEquals(MOCK_WATER_DRINK_TYPE_IS_ALCOHOL, isAlcohol)
}

fun mockBeerDrinkType(): DrinkType = mockk {
    every { drinkTypeUid } returns MOCK_BEER_DRINK_TYPE_UID
    every { name } returns MOCK_BEER_DRINK_TYPE_NAME
    every { hydration } returns MOCK_BEER_DRINK_TYPE_HYDRATION
    every { isAlcohol } returns MOCK_BEER_DRINK_TYPE_IS_ALCOHOL
}

fun DrinkType.assertBeerDrinkType() {
    assertEquals(MOCK_BEER_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(MOCK_BEER_DRINK_TYPE_NAME, name)
    assertEquals(MOCK_BEER_DRINK_TYPE_HYDRATION, hydration)
    assertEquals(MOCK_BEER_DRINK_TYPE_IS_ALCOHOL, isAlcohol)
}

fun mockTeaDrinkType(): DrinkType = mockk {
    every { drinkTypeUid } returns MOCK_TEA_DRINK_TYPE_UID
    every { name } returns MOCK_TEA_DRINK_TYPE_NAME
    every { hydration } returns MOCK_TEA_DRINK_TYPE_HYDRATION
    every { isAlcohol } returns MOCK_TEA_DRINK_TYPE_IS_ALCOHOL
}

fun DrinkType.assertTeaDrinkType() {
    assertEquals(MOCK_TEA_DRINK_TYPE_UID, drinkTypeUid)
    assertEquals(MOCK_TEA_DRINK_TYPE_NAME, name)
    assertEquals(MOCK_TEA_DRINK_TYPE_HYDRATION, hydration)
    assertEquals(MOCK_TEA_DRINK_TYPE_IS_ALCOHOL, isAlcohol)
}

const val MOCK_WATER_DRINK_TYPE_UID = 1
const val MOCK_WATER_DRINK_TYPE_NAME = "Water"
const val MOCK_WATER_DRINK_TYPE_HYDRATION = 1.0
const val MOCK_WATER_DRINK_TYPE_IS_ALCOHOL = false

const val MOCK_BEER_DRINK_TYPE_UID = 2
const val MOCK_BEER_DRINK_TYPE_NAME = "Beer"
const val MOCK_BEER_DRINK_TYPE_HYDRATION = -1.0
const val MOCK_BEER_DRINK_TYPE_IS_ALCOHOL = true

const val MOCK_TEA_DRINK_TYPE_UID = 3
const val MOCK_TEA_DRINK_TYPE_NAME = "Tea"
const val MOCK_TEA_DRINK_TYPE_HYDRATION = 1.0
const val MOCK_TEA_DRINK_TYPE_IS_ALCOHOL = false