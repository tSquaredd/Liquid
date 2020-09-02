package com.tsquaredapplications.liquid.add.amount.resources

import java.util.*

interface DrinkAmountResourceWrapper {
    val amountErrorMessage: String

    fun getMonthDisplayName(calendar: Calendar): String
}