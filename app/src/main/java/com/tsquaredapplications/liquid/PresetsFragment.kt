package com.tsquaredapplications.liquid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsquaredapplications.liquid.PresetsFragmentDirections.Companion.toAddPresetFragment
import com.tsquaredapplications.liquid.databinding.FragmentPresetsBinding
import com.tsquaredapplications.liquid.ext.navigate

class PresetsFragment : BaseFragment<FragmentPresetsBinding>() {

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPresetsBinding = FragmentPresetsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.createPresetFab.setOnClickListener {
            navigate(toAddPresetFragment())
        }
    }
}