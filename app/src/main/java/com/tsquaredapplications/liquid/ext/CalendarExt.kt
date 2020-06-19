package com.tsquaredapplications.liquid.ext

import java.util.*

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