package com.tsquaredapplications.liquid.presets.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.UserInformation
import com.tsquaredapplications.liquid.common.database.UserDatabaseManager
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState
import javax.inject.Inject

class AddPresetViewModel
@Inject constructor(private val userDatabaseManager: UserDatabaseManager) : ViewModel() {

    private val state = SingleEventLiveData<AddPresetState>()
    val stateLiveData: LiveData<AddPresetState>
        get() = state

    fun start() {
        // TODO find way to inject UserInformation instead of having to retrieve every time

        userDatabaseManager.getUser(
            onFail = {
                onUserDataFail()
            },
            onSuccess = { userInformation ->
                onUserDataSuccess(userInformation)
            }
        )
    }

    private fun onUserDataSuccess(userInformation: UserInformation) {
        state.value = AddPresetState.Initialized(userInformation.unitPreference)
    }

    private fun onUserDataFail() {
        state.value = AddPresetState.UserDataFail
    }
}