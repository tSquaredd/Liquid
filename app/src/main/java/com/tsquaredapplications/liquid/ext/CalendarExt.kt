package com.tsquaredapplications.liquid.ext

import android.content.Context
import com.tsquaredapplications.liquid.R
import java.util.*

val Calendar.year
    get() = get(Calendar.YEAR)

val Calendar.month
    get() = get(Calendar.MONTH)

val Calendar.day
    get() = get(Calendar.DAY_OF_MONTH)

fun getStartAndEndTimeForToday(): Pair<Long, Long> {
    with(Calendar.getInstance()) {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        val startTime = time

        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
        val endTime = time

        return startTime.time to endTime.time
    }
}

fun getEndTimeForToday(): Long =
    Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis

fun getStartOfDayFor(timestamp: Long) =
    Calendar.getInstance().apply {
        timeInMillis = timestamp
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

fun getEndTimeOfDayFor(timestamp: Long) =
    Calendar.getInstance().apply {
        timeInMillis = timestamp
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis

fun Calendar.getDayDisplayName(context: Context): String {
    return when (get(Calendar.DAY_OF_WEEK)) {
        1 -> context.getString(R.string.monday)
        2 -> context.getString(R.string.tuesday)
        3 -> context.getString(R.string.wednesday)
        4 -> context.getString(R.string.thursday)
        5 -> context.getString(R.string.friday)
        6 -> context.getString(R.string.saturday)
        7 -> context.getString(R.string.sunday)
        else -> ""
    }
}

fun Calendar.getMonthDisplayName(context: Context): String {
    return when (get(Calendar.MONTH)) {
        0 -> context.getString(R.string.january)
        1 -> context.getString(R.string.february)
        2 -> context.getString(R.string.march)
        3 -> context.getString(R.string.april)
        4 -> context.getString(R.string.may)
        5 -> context.getString(R.string.june)
        6 -> context.getString(R.string.july)
        7 -> context.getString(R.string.august)
        8 -> context.getString(R.string.september)
        9 -> context.getString(R.string.october)
        10 -> context.getString(R.string.november)
        11 -> context.getString(R.string.december)
        else -> ""
    }
}

fun Calendar.getDayDisplayNumber(): String {
    return when (val dayNumber = get(Calendar.DAY_OF_MONTH)) {
        1 -> "${dayNumber}st"
        2 -> "${dayNumber}nd"
        3 -> "${dayNumber}rd"
        21 -> "${dayNumber}st"
        22 -> "${dayNumber}nd"
        23 -> "${dayNumber}rd"
        31 -> "${dayNumber}st"
        else -> "${dayNumber}th"
    }
}