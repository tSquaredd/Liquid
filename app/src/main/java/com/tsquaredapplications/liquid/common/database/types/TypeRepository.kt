package com.tsquaredapplications.liquid.common.database.types

interface TypeRepository {
    suspend fun getAllDrinkTypesWithIcons(): Map<Int, DrinkTypeAndIcon>
    suspend fun getAllDrinkTypes(): Map<Int, DrinkType>
}