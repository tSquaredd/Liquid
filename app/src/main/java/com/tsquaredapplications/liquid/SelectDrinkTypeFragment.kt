package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.SelectDrinkTypeFragmentDirections.Companion.toDrinkAmountFragment
import com.tsquaredapplications.liquid.databinding.FragmentSelectDrinkTypeBinding
import com.tsquaredapplications.liquid.ext.navigate

class SelectDrinkTypeFragment : BaseFragment<FragmentSelectDrinkTypeBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectDrinkTypeBinding =
        FragmentSelectDrinkTypeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.drinkSelectedButton.setOnClickListener {
            navigate(toDrinkAmountFragment())
        }
    }
}