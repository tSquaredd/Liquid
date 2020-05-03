package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.HomeFragmentDirections.Companion.toDrinkTypeFragment
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentHomeBinding
import com.tsquaredapplications.liquid.ext.navigate

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addDrinkButton.setOnClickListener {
            navigate(toDrinkTypeFragment())
        }
    }
}