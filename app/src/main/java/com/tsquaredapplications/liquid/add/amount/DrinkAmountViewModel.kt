package com.tsquaredapplications.liquid.add.amount

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.DrinkAdded
import com.tsquaredapplications.liquid.add.amount.DrinkAmountState.UpdateDateString
import com.tsquaredapplications.liquid.add.amount.resources.DrinkAmountResourceWrapper
import com.tsquaredapplications.liquid.common.BaseViewModel
import com.tsquaredapplications.liquid.common.database.entry.Entry
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.ext.getDayDisplayNumber
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class DrinkAmountViewModel
@Inject constructor(
    private val userInformation: UserInformation,
    private val entryRepository: EntryRepository,
    private val resourceWrapper: DrinkAmountResourceWrapper
) : BaseViewModel<DrinkAmountState>(), DatePickerDialog.OnDateSetListener {

    @VisibleForTesting
    var calendar: Calendar = Calendar.getInstance()

    @VisibleForTesting
    var amount: Double? = null
    lateinit var drinkType: DrinkType

    fun start(drinkType: DrinkType) {
        this.drinkType = drinkType
        state.value =
            DrinkAmountState.Initialized(
                userInformation.unitPreference.toString(),
                buildDateString(),
                calendar
            )
    }

    private fun buildDateString(): String =
        "${resourceWrapper.getMonthDisplayName(calendar)} ${calendar.getDayDisplayNumber()}"

    fun onAmountChanged(amountString: String) {
        amount = amountString.toDoubleOrNull()
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        state.value = UpdateDateString(buildDateString())
    }
}

sealed class DrinkAmountState {
    class Initialized(val unitPreference: String, val dateString: String, val calendar: Calendar) :
        DrinkAmountState()

    class InvalidAmount(val errorMessage: String) : DrinkAmountState()
    class UpdateDateString(val dateString: String) : DrinkAmountState()
    object DrinkAdded : DrinkAmountState()
}