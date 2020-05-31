package com.tsquaredapplications.liquid.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.UserInformation
import com.tsquaredapplications.liquid.home.model.HomeState
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val resourceWrapper: HomeResourceWrapper
) : ViewModel() {

    private val state = SingleEventLiveData<HomeState>()
    val stateLiveData: LiveData<HomeState>
        get() = state

    fun start() {
        updateProgress()
    }

    @VisibleForTesting
    fun updateProgress() {
        state.value = HomeState.Initialized(
            goalProgress = resourceWrapper.getGoalProgressText(
                progress = 0,
                goal = userInformation.dailyGoal,
                unit = userInformation.unitPreference
            )
        )
    }
}