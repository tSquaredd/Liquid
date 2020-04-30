package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentSelectDrinkTypeBinding

/**
 * A simple [Fragment] subclass.
 */
class SelectDrinkTypeFragment : Fragment() {

    private var _binding: FragmentSelectDrinkTypeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectDrinkTypeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.drinkSelectedButton.setOnClickListener {
            val action =
                SelectDrinkTypeFragmentDirections.actionSelectDrinkTypeFragmentToDrinkAmountFragment()
            view.findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}