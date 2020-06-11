package com.tsquaredapplications.liquid.common.database.types

interface TypeDatabaseManager {
    fun getTypes(
        onSuccess: (List<DrinkType>) -> Unit,
        onFailure: (Exception) -> Unit
    )
}