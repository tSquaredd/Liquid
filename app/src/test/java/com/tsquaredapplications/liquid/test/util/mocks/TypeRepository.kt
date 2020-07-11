package com.tsquaredapplications.liquid.test.util.mocks

import com.tsquaredapplications.liquid.common.database.types.DrinkTypeAndIcon
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import io.mockk.coEvery
import io.mockk.mockk

fun mockTypeRepository(): TypeRepository = mockk {
    val drinkTypeAndIconMap = mutableMapOf<Int, DrinkTypeAndIcon>().apply {
        put(MOCK_WATER_DRINK_TYPE_UID, mockWaterDrinkTypeAndIcon())
        put(MOCK_BEER_DRINK_TYPE_UID, mockBeerDrinkTypeAndIcon())
        put(MOCK_TEA_DRINK_TYPE_UID, mockTeaDrinkTypeAndIcon())
    }

    coEvery { getAllDrinkTypesWithIcons() } returns drinkTypeAndIconMap
}