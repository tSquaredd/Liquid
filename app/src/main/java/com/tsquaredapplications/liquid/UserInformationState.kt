package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.login.LiquidUnit

sealed class UserInformationState {
    class InvalidWeight(val errorMessage: String) : UserInformationState()
    class Continue(val weight: Int, val unitChoiceState: LiquidUnit) : UserInformationState()
}