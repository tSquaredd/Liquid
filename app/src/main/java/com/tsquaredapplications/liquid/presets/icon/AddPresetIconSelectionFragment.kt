package com.tsquaredapplications.liquid.presets.icon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetIconSelectionBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.presets.icon.AddPresetIconSelectionFragmentDirections.Companion.toAddPresetFragment

class AddPresetIconSelectionFragment : BaseFragment<FragmentAddPresetIconSelectionBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetIconSelectionBinding =
        FragmentAddPresetIconSelectionBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.iconSelectedButton.setOnClickListener {
            navigate(toAddPresetFragment())
        }
    }
}