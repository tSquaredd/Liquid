package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.tsquaredapplications.liquid.databinding.FragmentHistoryBinding

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dayCardButton.setOnClickListener {
            val action = HistoryFragmentDirections.actionHistoryFragmentToDayHistoryFragment()
            view.findNavController().navigate(action)
        }
    }
}