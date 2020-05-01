package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetTypeSelectionBinding

/**
 * A simple [Fragment] subclass.
 */
class AddPresetTypeSelectionFragment : BaseFragment<FragmentAddPresetTypeSelectionBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetTypeSelectionBinding =
        FragmentAddPresetTypeSelectionBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.typeSelectionMadeButton.setOnClickListener {
            val action =
                AddPresetTypeSelectionFragmentDirections.actionAddPresetTypeSelectionFragmentToAddPresetFragment()
            view.findNavController().navigate(action)
        }
    }
}