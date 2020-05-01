package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentDrinkAmountBinding

/**
 * A simple [Fragment] subclass.
 */
class DrinkAmountFragment : BaseFragment<FragmentDrinkAmountBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDrinkAmountBinding = FragmentDrinkAmountBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addButton.setOnClickListener {
            val action = DrinkAmountFragmentDirections.actionDrinkAmountFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
    }
}