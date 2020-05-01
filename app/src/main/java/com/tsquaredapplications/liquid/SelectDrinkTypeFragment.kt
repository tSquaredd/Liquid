package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentSelectDrinkTypeBinding

class SelectDrinkTypeFragment : BaseFragment<FragmentSelectDrinkTypeBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSelectDrinkTypeBinding =
        FragmentSelectDrinkTypeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.drinkSelectedButton.setOnClickListener {
            val action =
                SelectDrinkTypeFragmentDirections.actionSelectDrinkTypeFragmentToDrinkAmountFragment()
            view.findNavController().navigate(action)
        }
    }
}