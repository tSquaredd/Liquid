package com.tsquaredapplications.liquid.settings.unit

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentLiquidUnitSettingBinding

class LiquidUnitSettingFragment : BaseFragment<FragmentLiquidUnitSettingBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLiquidUnitSettingBinding =
        FragmentLiquidUnitSettingBinding.inflate(inflater, container, false)
}