package com.tsquaredapplications.liquid.history.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.goal.Goal
import com.tsquaredapplications.liquid.common.database.goal.GoalRepository
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.icons.IconRepository
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
    private val iconRepository: IconRepository,
    private val historyResourceWrapper: HistoryResourceWrapper
) : BaseViewModel<HistoryState>() {

    fun start() {
        viewModelScope.launch {
            val entries = async { entryRepository.getAll() }
            val goals = async { goalRepository.getAll() }
            val icons = async { iconRepository.getAllIcons() }

            val dateSeparatedEntries =
                buildHistoryDayItems(
                    entries.await().toMutableList(),
                    goals.await().toMutableList(),
                    icons.await()
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
        goals: MutableList<Goal>,
        icons: Map<Int, Icon>
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

            val entriesForDay = getEntriesAfter(startTime.timeInMillis, entries, icons)
            if (entriesForDay.isNotEmpty()) {
                val dateString = historyResourceWrapper.getDayDisplayName(startTime)
                val progress = entriesForDay.map { it.first.entry.amount }.sum()
                val progressString = buildProgressString(progress.toInt())

                separatedEntries.add(
                    HistoryDayItem.Model(
                        dateString,
                        entriesForDay,
                        progressString
                    )
                )

                entries.removeAll(entriesForDay.map { it.first })
            }
            startTime.add(Calendar.DAY_OF_YEAR, -1)
        }

        return separatedEntries
    }

    private fun getEntriesAfter(
        timestamp: Long,
        entries: MutableList<EntryDataWrapper>,
        icons: Map<Int, Icon>
    ): List<Pair<EntryDataWrapper, Icon>> {
        val entriesForDay = mutableListOf<Pair<EntryDataWrapper, Icon>>()

        entries.forEach { entryDataWrapper ->
            val icon = icons[entryDataWrapper.preset?.iconUid
                ?: entryDataWrapper.drinkType.iconUid]
                ?: IconRepository.DEFAULT_ICON

            if (entryDataWrapper.entry.timestamp >= timestamp) {
                entriesForDay.add(Pair(entryDataWrapper, icon))
            }
        }
        return entriesForDay
    }

    private fun buildProgressString(progress: Int) =
        "$progress ${userInformation.unitPreference.name.toLowerCase(Locale.getDefault())}/ " +
                "${userInformation.dailyGoal} ${userInformation.unitPreference.name.toLowerCase(
                    Locale.getDefault()
                )}"
}

sealed class HistoryState {
    class Initialized(val historyItems: List<HistoryDayItem>) : HistoryState()
}