package com.tsquaredapplications.liquid.common.database.icons

import com.tsquaredapplications.liquid.R

interface IconRepository {
    suspend fun getAllIcons(): Map<Int, Icon>

    companion object {
        val DEFAULT_ICON =
            Icon(-1, R.drawable.drink_placeholder, R.drawable.drink_type_placeholder_large)
    }
}