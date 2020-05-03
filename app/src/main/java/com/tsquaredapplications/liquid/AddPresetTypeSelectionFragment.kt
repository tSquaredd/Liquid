package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.AddPresetTypeSelectionFragmentDirections.Companion.toAddPresetFragment
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetTypeSelectionBinding
import com.tsquaredapplications.liquid.ext.navigate

class AddPresetTypeSelectionFragment : BaseFragment<FragmentAddPresetTypeSelectionBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetTypeSelectionBinding =
        FragmentAddPresetTypeSelectionBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.typeSelectionMadeButton.setOnClickListener {
            navigate(toAddPresetFragment())
        }
    }
}