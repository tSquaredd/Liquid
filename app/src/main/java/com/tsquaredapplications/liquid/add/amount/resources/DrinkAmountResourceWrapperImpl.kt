package com.tsquaredapplications.liquid.add.amount.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.ext.getMonthDisplayName
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class DrinkAmountResourceWrapperImpl
@Inject constructor(
    @ApplicationContext private val context: Context) :
    DrinkAmountResourceWrapper {
    override val amountErrorMessage: String
        get() = context.getString(R.string.amount_error_message)

    override fun getMonthDisplayName(calendar: Calendar): String =
        calendar.getMonthDisplayName(context)
}