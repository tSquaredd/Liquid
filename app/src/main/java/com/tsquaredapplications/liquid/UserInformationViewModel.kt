package com.tsquaredapplications.liquid

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.login.LiquidUnit
import javax.inject.Inject

class UserInformationViewModel
@Inject constructor(val resourceWrapper: UserInformationResourceWrapper) : ViewModel() {

    private val state = SingleEventLiveData<UserInformationState>()
    val stateLiveData: LiveData<UserInformationState>
        get() = state

    private val unitChoiceState = SingleEventLiveData<LiquidUnit>()
    val unitStateLiveData: LiveData<LiquidUnit>
        get() = unitChoiceState

    fun start() {
        unitChoiceState.value = LiquidUnit.OZ
    }

    fun onUnitSelected(choice: LiquidUnit) {
        unitChoiceState.value = choice
    }

    fun onContinueClicked(weightInput: Editable?) {
        val weight = weightInput?.toString()?.toIntOrNull()
        if (weight == null) {
            state.value = UserInformationState.InvalidWeight(resourceWrapper.weightErrorMessage)
        } else {
            state.value = UserInformationState.Continue(weight, unitChoiceState.value!!)
        }
    }
}