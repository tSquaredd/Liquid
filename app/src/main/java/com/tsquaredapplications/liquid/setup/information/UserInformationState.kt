package com.tsquaredapplications.liquid.setup.information

import com.tsquaredapplications.liquid.setup.LiquidUnit

sealed class UserInformationState {
    class InvalidWeight(val errorMessage: String) : UserInformationState()
    class Continue(val weight: Int, val unitChoiceState: LiquidUnit) : UserInformationState()
}