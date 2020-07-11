package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.types.DrinkTypeAndIcon
import io.mockk.every
import io.mockk.mockk

fun mockWaterDrinkTypeAndIcon(): DrinkTypeAndIcon = mockk {
    every { drinkType } returns mockWaterDrinkType()
    every { icon } returns mockWaterIcon()
}

fun mockBeerDrinkTypeAndIcon(): DrinkTypeAndIcon = mockk {
    every { drinkType } returns mockBeerDrinkType()
    every { icon } returns mockBeerIcon()
}

fun mockTeaDrinkTypeAndIcon(): DrinkTypeAndIcon = mockk {
    every { drinkType } returns mockTeaDrinkType()
    every { icon } returns mockTeaIcon()
}