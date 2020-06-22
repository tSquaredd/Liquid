package com.tsquaredapplications.liquid.add.amount

import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.DrinkAdded
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.Entry
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DrinkAmountViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val entryRepository: EntryRepository,
    private val resourceWrapper: DrinkAmountResourceWrapper
) : BaseViewModel<DrinkAmountState>() {

    private var calendar = Calendar.getInstance()
    private var amount: Double? = null
    lateinit var drinkType: DrinkType

    fun start(drinkType: DrinkType) {
        this.drinkType = drinkType
        state.value = DrinkAmountState.Initialized(userInformation.unitPreference.name, calendar)
    }

    fun onDateChanged(year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

    fun onAddClicked() {
        if (amount == null || amount == 0.0) {
            state.value = DrinkAmountState.InvalidAmount(resourceWrapper.amountErrorMessage)
            return
        }

        viewModelScope.launch {
            entryRepository.insert(
                Entry(
                    amount = amount!!,
                    timestamp = calendar.time.time,
                    drinkTypeUid = drinkType.drinkTypeUid
                )
            )

            state.value = DrinkAdded
        }
    }

    fun onAmountChanged(amountString: String) {
        amount = amountString.toDoubleOrNull()
    }
}