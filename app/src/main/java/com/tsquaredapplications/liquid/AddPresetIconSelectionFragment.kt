package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetIconSelectionBinding

/**
 * A simple [Fragment] subclass.
 */
class AddPresetIconSelectionFragment : BaseFragment<FragmentAddPresetIconSelectionBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetIconSelectionBinding =
        FragmentAddPresetIconSelectionBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.iconSelectedButton.setOnClickListener {
            val action =
                AddPresetIconSelectionFragmentDirections.actionAddPresetIconSelectionFragmentToAddPresetFragment()
            view.findNavController().navigate(action)
        }
    }
}