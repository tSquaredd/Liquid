package com.tsquaredapplications.liquid.common.database.icons

interface IconRepository {
    suspend fun getAllIcons(): List<Icon>
}