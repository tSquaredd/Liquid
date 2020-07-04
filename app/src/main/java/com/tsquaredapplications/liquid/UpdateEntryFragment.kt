package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentUpdateEntryBinding

class UpdateEntryFragment : BaseFragment<FragmentUpdateEntryBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateEntryBinding = FragmentUpdateEntryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.deleteButton.setOnClickListener {
//            navigate(toDayHistoryFragment())
        }

        binding.updateButton.setOnClickListener {
//            navigate(toDayHistoryFragment())
        }
    }
}