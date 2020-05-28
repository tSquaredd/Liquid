package com.tsquaredapplications.liquid.home.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.login.LiquidUnit
import javax.inject.Inject

class HomeResourceWrapperImpl
@Inject constructor(val context: Context) : HomeResourceWrapper {
    override fun getGoalProgressText(progress: Int, goal: Int, unit: LiquidUnit): String {
        val progressFormat = context.getString(R.string.progress)
        return String.format(progressFormat, progress, goal, unit)
    }
}