package com.tsquaredapplications.liquid.history.main.resources

import android.content.Context
import com.tsquaredapplications.liquid.ext.getDayDisplayName
import com.tsquaredapplications.liquid.ext.getDayDisplayNumber
import com.tsquaredapplications.liquid.ext.getMonthDisplayName
import java.util.*
import javax.inject.Inject

class HistoryResourceWrapperImpl
@Inject constructor(private val context: Context) : HistoryResourceWrapper {
    override fun getDayDisplayName(calendar: Calendar): String {
        val dayName = calendar.getDayDisplayName(context)
        val dayNumber = calendar.getDayDisplayNumber()
        val monthName = calendar.getMonthDisplayName(context)
        val year = calendar.get(Calendar.YEAR)
        return "$dayName, $monthName $dayNumber $year"
    }
}