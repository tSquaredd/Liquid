package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetBinding

class AddPresetFragment : BaseFragment<FragmentAddPresetBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetBinding = FragmentAddPresetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPresetButton.setOnClickListener {
            val action = AddPresetFragmentDirections.actionAddPresetFragmentToPresetsFragment()
            view.findNavController().navigate(action)
        }

        binding.selectTypeButton.setOnClickListener {
            val action =
                AddPresetFragmentDirections.actionAddPresetFragmentToAddPresetTypeSelectionFragment()
            view.findNavController().navigate(action)
        }

        binding.selectIconButton.setOnClickListener {
            val action =
                AddPresetFragmentDirections.actionAddPresetFragmentToAddPresetIconSelectionFragment()
            view.findNavController().navigate(action)
        }
    }
}