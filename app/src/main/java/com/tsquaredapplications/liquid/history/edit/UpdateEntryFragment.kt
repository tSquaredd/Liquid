package com.tsquaredapplications.liquid.history.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.databinding.FragmentUpdateEntryBinding
import com.tsquaredapplications.liquid.ext.keyboardHidingFocusChangeListener
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.EntryDeleted
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.EntryUpdated
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.Initialized
import com.tsquaredapplications.liquid.history.edit.UpdateEntryState.InvalidAmount
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdateEntryFragment : BaseFragment<FragmentUpdateEntryBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<UpdateEntryViewModel> { viewModelFactory }
    private val args by navArgs<UpdateEntryFragmentArgs>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUpdateEntryBinding = FragmentUpdateEntryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.deleteButton.setOnClickListener {
            viewModel.onDeleteClicked()
        }

        binding.updateButton.setOnClickListener {
            viewModel.onUpdateClicked()
        }

        with(binding.amountSelectionEditText) {
            addTextChangedListener {
                it?.let {
                    viewModel.onAmountChanged(it.toString())
                    binding.amountSelectionTextLayout.error = ""
                }
            }
            onFocusChangeListener = keyboardHidingFocusChangeListener
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start(args.entry)
    }

    private fun onStateChanged(state: UpdateEntryState?) {
        when (state) {
            is Initialized -> onInitialized(state)
            is EntryDeleted -> onEntryDeleted()
            is InvalidAmount -> onInvalidAmount(state)
            is EntryUpdated -> onEntryUpdated()
        }
    }

    private fun onInitialized(state: Initialized) {
        binding.name.text = state.name
        binding.amountSelectionEditText.setText(state.amount)
        binding.amountSelectionTextLayout.hint = state.liquidUnit

        GlideApp.with(binding.icon.context)
            .load(state.icon.largeIconResource)
            .placeholder(R.drawable.drink_type_placeholder_large)
            .into(binding.icon)
    }

    private fun onEntryDeleted() {
        if (args.isOnlyEntryForDay) {
            navigate(UpdateEntryFragmentDirections.toHistoryFragment())
        } else {
            findNavController().popBackStack()
        }
    }

    private fun onInvalidAmount(state: InvalidAmount) {
        binding.amountSelectionTextLayout.error = state.errorMessage
    }

    private fun onEntryUpdated() {
        findNavController().popBackStack()
    }
}