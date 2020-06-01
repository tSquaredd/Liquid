package com.tsquaredapplications.liquid.common.database.types

interface TypeDatabaseManager {
    fun getTypes(
        onSuccess: (List<Type>) -> Unit,
        onFailure: (Exception) -> Unit
    )
}