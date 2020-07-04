package com.tsquaredapplications.liquid.history.main.resources

import java.util.*

interface HistoryResourceWrapper {
    fun getDayDisplayName(calendar: Calendar): String
}