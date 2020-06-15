package com.tsquaredapplications.liquid.common.database.icons

import androidx.lifecycle.LiveData

interface IconRepository {
    fun getAllIcons(): LiveData<List<Icon>>
}