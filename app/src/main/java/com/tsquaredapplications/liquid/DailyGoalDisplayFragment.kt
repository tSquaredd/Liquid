package com.tsquaredapplications.liquid

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentDailyGoalDisplayBinding

class DailyGoalDisplayFragment : BaseFragment<FragmentDailyGoalDisplayBinding>() {
    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDailyGoalDisplayBinding =
        FragmentDailyGoalDisplayBinding.inflate(inflater, container, false)

}
