package com.tsquaredapplications.liquid.history.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.goal.Goal
import com.tsquaredapplications.liquid.common.database.goal.GoalRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.ext.getEndTimeForToday
import com.tsquaredapplications.liquid.history.main.HistoryState.Initialized
import com.tsquaredapplications.liquid.history.main.resources.HistoryResourceWrapper
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class HistoryViewModel
@Inject constructor(
    private val entryRepository: EntryRepository,
    private val goalRepository: GoalRepository,
    private val userInformation: UserInformation,
    private val historyResourceWrapper: HistoryResourceWrapper
) : BaseViewModel<HistoryState>() {

    fun start() {
        viewModelScope.launch {
            val entries = async { entryRepository.getAll() }
            val goals = async { goalRepository.getAll() }

            val dateSeparatedEntries =
                buildHistoryDayItems(
                    entries.await().toMutableList(),
                    goals.await().toMutableList()
                )

            state.value = Initialized(
                dateSeparatedEntries.map { model ->
                    HistoryDayItem(model)
                }
            )
        }
    }

    @VisibleForTesting
    fun buildHistoryDayItems(
        entries: MutableList<EntryDataWrapper>,
        goals: MutableList<Goal>
    ): List<HistoryDayItem.Model> {

        val separatedEntries =
            mutableListOf<HistoryDayItem.Model>()

        val startTime = Calendar.getInstance()
        startTime.timeInMillis = getEndTimeForToday()

        var currentGoal = goals.first()
        goals.removeAt(0)

        while (entries.isNotEmpty()) {
            if (startTime.timeInMillis < currentGoal.startTimeStamp && goals.isNotEmpty()) {
                currentGoal = goals.first()
                goals.removeAt(0)
            }

            val entriesForDay = getEntriesAfter(startTime.timeInMillis, entries)
            if (entriesForDay.isNotEmpty()) {
                val dateString = historyResourceWrapper.getDayDisplayName(startTime)
                val progress = entriesForDay.map { it.entry.amount }.sum()
                val progressString = buildProgressString(progress.toInt())

                separatedEntries.add(
                    HistoryDayItem.Model(
                        dateString,
                        entriesForDay.sortedBy { it.entry.timestamp },
                        progressString,
                        userInformation.unitPreference
                    )
                )

                entries.removeAll(entriesForDay.map { it })
            }
            startTime.add(Calendar.DAY_OF_YEAR, -1)
        }

        return separatedEntries
    }

    private fun getEntriesAfter(
        timestamp: Long,
        entries: MutableList<EntryDataWrapper>
    ): List<EntryDataWrapper> {
        val entriesForDay = mutableListOf<EntryDataWrapper>()

        entries.forEach { entryDataWrapper ->
            if (entryDataWrapper.entry.timestamp >= timestamp) {
                entriesForDay.add(entryDataWrapper)
            }
        }
        return entriesForDay
    }

    private fun buildProgressString(progress: Int) =
        "$progress ${userInformation.unitPreference} / " +
                "${userInformation.dailyGoal} ${userInformation.unitPreference}"
}

sealed class HistoryState {
    class Initialized(val historyItems: List<HistoryDayItem>) : HistoryState()
}