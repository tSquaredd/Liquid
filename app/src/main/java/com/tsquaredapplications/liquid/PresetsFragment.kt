package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentPresetsBinding

class PresetsFragment : BaseFragment<FragmentPresetsBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPresetsBinding = FragmentPresetsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createPresetFab.setOnClickListener {
            val action = PresetsFragmentDirections.actionPresetsFragmentToAddPresetFragment()
            view.findNavController().navigate(action)
        }
    }
}