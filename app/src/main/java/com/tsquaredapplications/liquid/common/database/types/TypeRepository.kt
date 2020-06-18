package com.tsquaredapplications.liquid.common.database.types

interface TypeRepository {
    suspend fun getAllTypes(): List<DrinkTypeAndIcon>
}