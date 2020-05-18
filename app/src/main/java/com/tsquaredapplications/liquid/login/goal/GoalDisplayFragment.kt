package com.tsquaredapplications.liquid.login.goal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.ErrorDialogFragment
import com.tsquaredapplications.liquid.databinding.FragmentGoalDisplayBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisibile
import com.tsquaredapplications.liquid.login.LoginActivity
import com.tsquaredapplications.liquid.login.goal.GoalDisplayFragmentDirections.Companion.toMainActivity
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.DataFail
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.DataSuccess
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.HideProgressBar
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.Initialized
import com.tsquaredapplications.liquid.login.goal.GoalDisplayState.ShowProgressBar
import javax.inject.Inject

class GoalDisplayFragment : BaseFragment<FragmentGoalDisplayBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: GoalDisplayViewModel by viewModels { viewModelFactory }
    private val args: GoalDisplayFragmentArgs by navArgs()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGoalDisplayBinding =
        FragmentGoalDisplayBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as LoginActivity).loginComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finishButton.setOnClickListener {
            viewModel.onFinishClick()
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start(args.weight, args.unitChoice)
    }

    private fun onStateChanged(state: GoalDisplayState) {
        when (state) {
            is Initialized -> displayGoal(state.goal)
            is DataSuccess -> navigate(toMainActivity())
            is DataFail -> onDatabaseFail(state)
            is ShowProgressBar -> showProgressBar()
            is HideProgressBar -> hideProgressBar()
        }
    }

    private fun onDatabaseFail(state: DataFail) {
        ErrorDialogFragment(state.errorMessage, state.dismissText)
            .show(parentFragmentManager, null)
    }

    private fun hideProgressBar() {
        binding.progressBar.setAsGone()
        binding.loadingMask.setAsGone()
    }

    private fun showProgressBar() {
        binding.progressBar.setAsVisibile()
        binding.loadingMask.setAsVisibile()
    }

    private fun displayGoal(goal: String) {
        binding.goalAmount.text = goal
    }
}
