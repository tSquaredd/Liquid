package com.tsquaredapplications.liquid

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.login.UnitChoiceState
import javax.inject.Inject

class UserInformationViewModel
@Inject constructor() : ViewModel() {

    private val unitChoiceLiveData = SingleEventLiveData<UnitChoiceState>()
    fun getUnitChoiceLiveData(): LiveData<UnitChoiceState> = unitChoiceLiveData

    fun start() {
        unitChoiceLiveData.value = UnitChoiceState.OZ
    }

    fun onUnitSelected(choice: UnitChoiceState) {
        unitChoiceLiveData.value = choice
    }
}