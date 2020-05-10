package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.HistoryFragmentDirections.Companion.toDayHistoryFragment
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentHistoryBinding
import com.tsquaredapplications.liquid.ext.navigate

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dayCardButton.setOnClickListener {
            navigate(toDayHistoryFragment())
        }
    }
}