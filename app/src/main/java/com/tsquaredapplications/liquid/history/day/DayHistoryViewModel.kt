package com.tsquaredapplications.liquid.history.day

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.history.day.DayHistoryState.Initialized
import com.tsquaredapplications.liquid.history.main.HistoryIconItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class DayHistoryViewModel
@Inject constructor(
    private val entryRepository: EntryRepository,
    private val userInformation: UserInformation,
    private val resourceWrapper: DayHistoryResourceWrapper
) :
    BaseViewModel<DayHistoryState>() {

    fun start(timestampRange: TimestampRange) {
        viewModelScope.launch {
            val entryDataWrappers =
                entryRepository.getAllInTimeRange(timestampRange.startTime, timestampRange.endTime)
            state.value = Initialized(
                screenTitle = resourceWrapper.getScreenTitle(entryDataWrappers.first().entry.timestamp),
                historyIconModels = entryDataWrappers.map {
                    HistoryIconItem(HistoryIconItem.Model(it, userInformation.unitPreference, true))
                })
        }
    }
}

sealed class DayHistoryState {
    class Initialized(val screenTitle: String, val historyIconModels: List<HistoryIconItem>) :
        DayHistoryState()
}