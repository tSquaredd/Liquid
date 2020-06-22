package com.tsquaredapplications.liquid.home.resources

import android.content.Context
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.setup.LiquidUnit
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

class HomeResourceWrapperImpl
@Inject constructor(val context: Context) : HomeResourceWrapper {
    override fun getGoalProgressText(progress: Double, goal: Int, unit: LiquidUnit): String {
        val progressFormat = context.getString(R.string.progress)
        val progressString = DecimalFormat("#.##").format(progress)

        return String.format(
            progressFormat,
            progressString,
            goal,
            unit.name.toLowerCase(Locale.getDefault())
        )
    }
}