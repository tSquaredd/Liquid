package com.tsquaredapplications.liquid.settings.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentWeightSettingBinding
import com.tsquaredapplications.liquid.ext.keyboardHidingFocusChangeListener
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.settings.weight.WeightSettingFragmentDirections.Companion.toGoalUpdatedFragment
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.DisableUpdateButton
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.EnabledUpdateButton
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.GoalUpdated
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.Initialized
import com.tsquaredapplications.liquid.settings.weight.WeightSettingState.ShowGoalCalculationPrompt
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeightSettingFragment : BaseFragment<FragmentWeightSettingBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<WeightSettingViewModel> { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeightSettingBinding =
        FragmentWeightSettingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.weightSelectionEditText) {
            addTextChangedListener {
                viewModel.onWeightUpdated(it.toString())
            }

            onFocusChangeListener = keyboardHidingFocusChangeListener
        }

        binding.updateButton.setOnClickListener {
            viewModel.onUpdateClicked()
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start()
    }

    private fun onStateChanged(state: WeightSettingState?) {
        when (state) {
            is Initialized -> onInitialized(state)
            is EnabledUpdateButton -> binding.updateButton.isEnabled = true
            is DisableUpdateButton -> binding.updateButton.isEnabled = false
            is ShowGoalCalculationPrompt -> showGoalCalculationPrompt()
            is GoalUpdated -> onGoalUpdated(state)
        }
    }

    private fun onInitialized(state: Initialized) {
        binding.weightSelectionEditText.setText(state.weight.toString())
    }

    private fun showGoalCalculationPrompt() {
        WeightChangeGoalCalculationDialog(
            onConfirm = {
                viewModel.onCalculateNewGoal()
            },
            onDecline = {
                findNavController().popBackStack()
            }
        ).show(parentFragmentManager, null)
    }

    private fun onGoalUpdated(state: GoalUpdated) {
        navigate(toGoalUpdatedFragment(state.goal))
    }
}
