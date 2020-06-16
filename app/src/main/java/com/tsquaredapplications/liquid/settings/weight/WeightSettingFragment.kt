package com.tsquaredapplications.liquid.settings.weight

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentWeightSettingBinding

class WeightSettingFragment : BaseFragment<FragmentWeightSettingBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeightSettingBinding =
        FragmentWeightSettingBinding.inflate(inflater, container, false)
}