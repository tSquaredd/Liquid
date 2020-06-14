package com.tsquaredapplications.liquid.common.database.types

import androidx.lifecycle.LiveData

interface TypeRepository {
    fun getAllTypes(): LiveData<List<DrinkTypeAndIcon>>
}