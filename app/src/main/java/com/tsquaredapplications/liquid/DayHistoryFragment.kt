package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.DayHistoryFragmentDirections.Companion.toUpdateEntryFragment
import com.tsquaredapplications.liquid.databinding.FragmentDayHistoryBinding
import com.tsquaredapplications.liquid.ext.navigate

class DayHistoryFragment : BaseFragment<FragmentDayHistoryBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDayHistoryBinding = FragmentDayHistoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.entryButton.setOnClickListener {
            navigate(toUpdateEntryFragment())
        }
    }
}