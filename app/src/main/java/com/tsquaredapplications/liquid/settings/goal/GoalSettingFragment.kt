package com.tsquaredapplications.liquid.settings.goal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentGoalSettingBinding
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.Finished
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.Initialized
import com.tsquaredapplications.liquid.settings.goal.GoalSettingState.InvalidAmount
import javax.inject.Inject

class GoalSettingFragment : BaseFragment<FragmentGoalSettingBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GoalSettingViewModel> { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGoalSettingBinding =
        FragmentGoalSettingBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        (activity as MainActivity).mainComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateButton.setOnClickListener {
            viewModel.update()
        }

        binding.goalSelectionEditText.addTextChangedListener { editable ->
            editable?.toString()?.let { viewModel.onGoalInputChanged(it) }
            binding.goalSelectionTextLayout.error = ""
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start()
    }

    private fun onStateChanged(state: GoalSettingState?) {
        when (state) {
            is Initialized -> onInitialized(state)
            is InvalidAmount -> onInvalidAmount(state)
            is Finished -> findNavController().popBackStack()
        }
    }

    private fun onInitialized(state: Initialized) {
        binding.goalSelectionTextLayout.hint = state.unitPreference
        binding.goalSelectionEditText.text?.let {
            it.clear()
            it.insert(0, state.currentGoal)
        }
    }

    private fun onInvalidAmount(state: InvalidAmount) {
        binding.goalSelectionTextLayout.error = state.errorMessage
    }
}