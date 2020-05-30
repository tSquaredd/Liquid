package com.tsquaredapplications.liquid.presets.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.presets.main.AddPresetFragmentDirections.Companion.toAddPresetIconSelectionFramgent
import com.tsquaredapplications.liquid.presets.main.AddPresetFragmentDirections.Companion.toAddPresetTypeSelectionFragment
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState
import com.tsquaredapplications.liquid.presets.main.model.AddPresetState.Initialized
import javax.inject.Inject

class AddPresetFragment : BaseFragment<FragmentAddPresetBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AddPresetViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetBinding = FragmentAddPresetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.presetIcon.setOnClickListener {
            navigate(toAddPresetIconSelectionFramgent())
        }

        with(binding.typeSelectionEditText) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) navigate(toAddPresetTypeSelectionFragment())
            }
            showSoftInputOnFocus = false
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer { state ->
            onStateChange(state)
        })

        viewModel.start()
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).mainComponent.inject(this)
        super.onAttach(context)
    }

    private fun onStateChange(state: AddPresetState) {
        when (state) {
            is Initialized -> onInitializedState(state)
        }
    }

    private fun onInitializedState(state: Initialized) {
        binding.amountSelectionTextLayout.hint = state.unit.name
    }
}