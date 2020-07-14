package com.tsquaredapplications.liquid.home.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.ext.toTwoDigitDecimalString
import javax.inject.Inject

class HomeResourceWrapperImpl
@Inject constructor(val context: Context) : HomeResourceWrapper {
    override fun getGoalProgressText(progress: Double, goal: Int, unit: LiquidUnit): String {
        val progressFormat = context.getString(R.string.progress)
        val progressString = if (progress != 0.0) progress.toTwoDigitDecimalString() else "0"

        return String.format(
            progressFormat,
            progressString,
            goal,
            unit.name
        )
    }
}