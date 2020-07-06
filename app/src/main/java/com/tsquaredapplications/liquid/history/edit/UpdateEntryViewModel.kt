package com.tsquaredapplications.liquid.history.edit

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.EntryDeleted
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.EntryUpdated
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.Initialized
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.InvalidAmount
import com.tsquaredapplications.liquid.history.edit.resources.UpdateEntryResourceWrapper
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class UpdateEntryViewModel
@Inject constructor(
    private val entryRepository: EntryRepository,
    private val userInformation: UserInformation,
    private val resourceWrapper: UpdateEntryResourceWrapper
) :
    BaseViewModel<UpdateEntryState>() {

    private lateinit var entryDataWrapper: EntryDataWrapper
    private var selectedAmount: Double? = 0.0

    fun start(entryDataWrapper: EntryDataWrapper) {
        this.entryDataWrapper = entryDataWrapper
        selectedAmount = entryDataWrapper.entry.amount

        state.value = Initialized(
            entryDataWrapper.preset?.name ?: entryDataWrapper.drinkType.name,
            entryDataWrapper.icon,
            selectedAmount.toString(),
            userInformation.unitPreference.name.toLowerCase(Locale.getDefault())
        )
    }

    fun onDeleteClicked() {
        viewModelScope.launch {
            entryRepository.delete(entryDataWrapper.entry)
            state.value = EntryDeleted
        }
    }

    fun onUpdateClicked() {
        if (selectedAmount == null) {
            state.value = InvalidAmount(resourceWrapper.invalidAmountErrorMessage)
            return
        }

        viewModelScope.launch {
            entryRepository.update(entryDataWrapper.entry.apply {
                selectedAmount?.let { amount = it }
            })
            state.value = EntryUpdated
        }
    }

    fun onAmountChanged(amountString: String) {
        selectedAmount = amountString.toDoubleOrNull()
    }
}

sealed class UpdateEntryState {
    class Initialized(
        val name: String,
        val icon: Icon,
        val amount: String,
        val liquidUnit: String
    ) : UpdateEntryState()

    object EntryDeleted : UpdateEntryState()
    object EntryUpdated : UpdateEntryState()
    class InvalidAmount(val errorMessage: String) : UpdateEntryState()
}