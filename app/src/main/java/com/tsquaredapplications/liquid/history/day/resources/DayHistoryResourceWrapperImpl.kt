package com.tsquaredapplications.liquid.history.day.resources

import android.content.Context
import com.tsquaredapplications.liquid.ext.getDayDisplayNumber
import com.tsquaredapplications.liquid.ext.getMonthDisplayName
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class DayHistoryResourceWrapperImpl
@Inject constructor(
    @ApplicationContext private val context: Context) :
    DayHistoryResourceWrapper {
    override fun getScreenTitle(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return "${calendar.getMonthDisplayName(context)} ${calendar.getDayDisplayNumber()}"
    }
}