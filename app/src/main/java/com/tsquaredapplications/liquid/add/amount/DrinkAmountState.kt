package com.tsquaredapplications.liquid.add.amount

import java.util.*

sealed class DrinkAmountState {
    class Initialized(val unitPreference: String, val today: Calendar) : DrinkAmountState()
    class InvalidAmount(val errorMessage: String) : DrinkAmountState()
    object DrinkAdded : DrinkAmountState()
}