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
class DrinkAmountFragment : Fragment() {

    private var _binding: FragmentDrinkAmountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrinkAmountBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.addButton.setOnClickListener {
            val action = DrinkAmountFragmentDirections.actionDrinkAmountFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}