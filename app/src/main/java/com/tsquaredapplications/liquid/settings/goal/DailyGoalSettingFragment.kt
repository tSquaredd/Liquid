package com.tsquaredapplications.liquid.settings.goal

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentDailyGoalSettingBinding

class DailyGoalSettingFragment : BaseFragment<FragmentDailyGoalSettingBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDailyGoalSettingBinding =
        FragmentDailyGoalSettingBinding.inflate(inflater, container, false)
}