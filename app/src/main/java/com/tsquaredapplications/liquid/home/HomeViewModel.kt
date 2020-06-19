package com.tsquaredapplications.liquid.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.SingleEventLiveData
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.ext.getStartAndEndTimeForToday
import com.tsquaredapplications.liquid.home.model.HomeState
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val resourceWrapper: HomeResourceWrapper,
    private val entryRepository: EntryRepository
) : ViewModel() {

    private val state = SingleEventLiveData<HomeState>()
    val stateLiveData: LiveData<HomeState>
        get() = state

    fun start() {
        updateProgress()
    }

    @VisibleForTesting
    fun updateProgress() {
        viewModelScope.launch {
            val (startTime, endTime) = getStartAndEndTimeForToday()
            val todayEntries = entryRepository.getAllInTimeRange(from = startTime, to = endTime)
            val progress = todayEntries.map { it.amountInOz }.sum()

            state.value = HomeState.Initialized(
                goalProgress = resourceWrapper.getGoalProgressText(
                    progress = progress,
                    goal = userInformation.dailyGoal,
                    unit = userInformation.unitPreference
                )
            )
        }
    }
}