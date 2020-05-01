package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentUpdateEntryBinding

class UpdateEntryFragment : BaseFragment<FragmentUpdateEntryBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateEntryBinding = FragmentUpdateEntryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.deleteButton.setOnClickListener {
            val action =
                UpdateEntryFragmentDirections.actionUpdateEntryFragmentToDayHistoryFragment()
            view.findNavController().navigate(action)
        }

        binding.updateButton.setOnClickListener {
            val action =
                UpdateEntryFragmentDirections.actionUpdateEntryFragmentToDayHistoryFragment()
            view.findNavController().navigate(action)
        }
    }
}