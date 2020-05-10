package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.DrinkAmountFragmentDirections.Companion.toHomeFragment
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentDrinkAmountBinding
import com.tsquaredapplications.liquid.ext.navigate

class DrinkAmountFragment : BaseFragment<FragmentDrinkAmountBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDrinkAmountBinding = FragmentDrinkAmountBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addButton.setOnClickListener {
            navigate(toHomeFragment())
        }
    }
}