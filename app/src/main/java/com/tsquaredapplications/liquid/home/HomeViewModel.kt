package com.tsquaredapplications.liquid.home

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.ext.getStartAndEndTimeForToday
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState.Initialize
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

class HomeViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val resourceWrapper: HomeResourceWrapper,
    private val entryRepository: EntryRepository
) : BaseViewModel<HomeViewModel.HomeState>() {

    fun start(withAnimation: Boolean) {
        state.value = Initialize(
            hydratingText = resourceWrapper.hydratingText,
            dehydratingText = resourceWrapper.dehydratingText
        )

        updateProgress(withAnimation)
    }

    private fun updateProgress(withAnimation: Boolean) {
        viewModelScope.launch {
            val (startTime, endTime) = getStartAndEndTimeForToday()
            val todayEntries = entryRepository.getAllInTimeRange(from = startTime, to = endTime)
            val progress = todayEntries.map { it.entry.amount * it.drinkType.hydration }.sum()
            val percentComplete = max(((progress / userInformation.dailyGoal) * 100).toInt(), 0)

            if (!withAnimation) {
                state.value = HomeState.SetProgress(
                    goalPercentage = percentComplete,
                    hydratingAmount = todayEntries.totalHydratingAmount(),
                    dehydratingAmount = todayEntries.totalDehydratingAmount()
                )
            } else {
                val mostRecentEntry = todayEntries.lastOrNull()
                val previousPercentComplete = if (mostRecentEntry == null) {
                    0
                } else {
                    val previousProgress =
                        progress - (mostRecentEntry.entry.amount.toInt() * mostRecentEntry.drinkType.hydration)
                    max(((previousProgress / userInformation.dailyGoal) * 100).toInt(), 0)
                }

                val currentHydratingAmount = todayEntries.totalHydratingAmount()
                val previousHydratingAmount: Int = when {
                    mostRecentEntry == null -> 0
                    mostRecentEntry.drinkType.hydration < 0 -> currentHydratingAmount
                    else -> {
                        currentHydratingAmount - (mostRecentEntry.entry.amount * mostRecentEntry.drinkType.hydration).toInt()
                    }
                }

                val currentDehydratingAmount = todayEntries.totalDehydratingAmount()
                val previousDehydratingAmount: Int = when {
                    mostRecentEntry == null -> 0
                    mostRecentEntry.drinkType.hydration > 0 -> currentDehydratingAmount
                    else -> {
                        currentDehydratingAmount - (mostRecentEntry.entry.amount * -(mostRecentEntry.drinkType.hydration)).toInt()
                    }
                }

                state.value = HomeState.UpdateProgress(
                    percentComplete,
                    previousPercentComplete,
                    currentHydratingAmount,
                    previousHydratingAmount,
                    currentDehydratingAmount,
                    previousDehydratingAmount
                )
            }
        }
    }

    private fun List<EntryDataWrapper>.totalHydratingAmount(): Int =
        filter { it.drinkType.hydration > 0 }.map { it.entry.amount * it.drinkType.hydration }.sum()
            .toInt()

    private fun List<EntryDataWrapper>.totalDehydratingAmount(): Int =
        filter { it.drinkType.hydration < 0 }.map { it.entry.amount * -(it.drinkType.hydration) }
            .sum().toInt()

    sealed class HomeState {
        class Initialize(
            val hydratingText: String,
            val dehydratingText: String
        ) : HomeState()

        class SetProgress(
            val goalPercentage: Int,
            val hydratingAmount: Int,
            val dehydratingAmount: Int
        ) : HomeState()

        class UpdateProgress(
            val currentGoalPercentage: Int,
            val previousGoalPercentage: Int,
            val currentHydratingAmount: Int,
            val previousHydratingAmount: Int,
            val currentDehydratingAmount: Int,
            val previousDehydratingAmount: Int
        ) : HomeState()
    }
}