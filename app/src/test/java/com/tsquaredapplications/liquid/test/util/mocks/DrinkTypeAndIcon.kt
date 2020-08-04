package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.types.DrinkTypeAndIcon
import io.mockk.every
import io.mockk.mockk

fun mockWaterDrinkTypeAndIcon(): DrinkTypeAndIcon = mockk {
    every { drinkType } returns mockWaterDrinkType()
    every { icon } returns mockWaterIcon()
}

fun DrinkTypeAndIcon.assertWaterDrinkTypeAndIcon() {
    drinkType.assertWaterDrinkType()
    icon.assertWaterIcon()
}

fun mockBeerDrinkTypeAndIcon(): DrinkTypeAndIcon = mockk {
    every { drinkType } returns mockBeerDrinkType()
    every { icon } returns mockBeerIcon()
}

fun DrinkTypeAndIcon.assertBeerDrinkTypeAndIcon() {
    drinkType.assertBeerDrinkType()
    icon.assertBeerIcon()
}

fun mockTeaDrinkTypeAndIcon(): DrinkTypeAndIcon = mockk {
    every { drinkType } returns mockTeaDrinkType()
    every { icon } returns mockTeaIcon()
}

fun DrinkTypeAndIcon.assertTeaDrinkTypeAndIcon() {
    drinkType.assertTeaDrinkType()
    icon.assertTeaIcon()
}