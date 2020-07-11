package com.tsquaredapplications.liquid.stats.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentStatsBinding

class StatsFragment : BaseFragment<FragmentStatsBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatsBinding = FragmentStatsBinding.inflate(inflater, container, false)

}