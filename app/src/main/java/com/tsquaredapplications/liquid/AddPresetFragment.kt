package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.AddPresetFragmentDirections.Companion.toAddPresetIconSelectionFramgent
import com.tsquaredapplications.liquid.AddPresetFragmentDirections.Companion.toAddPresetTypeSelectionFragment
import com.tsquaredapplications.liquid.AddPresetFragmentDirections.Companion.toPresetsFragment
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetBinding
import com.tsquaredapplications.liquid.ext.navigate

class AddPresetFragment : BaseFragment<FragmentAddPresetBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetBinding = FragmentAddPresetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addPresetButton.setOnClickListener {
            navigate(toPresetsFragment())
        }

        binding.selectTypeButton.setOnClickListener {
            navigate(toAddPresetTypeSelectionFragment())
        }

        binding.selectIconButton.setOnClickListener {
            navigate(toAddPresetIconSelectionFramgent())
        }
    }
}