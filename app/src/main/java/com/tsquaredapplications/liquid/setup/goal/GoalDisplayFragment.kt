package com.tsquaredapplications.liquid.setup.goal

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
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.databinding.FragmentGoalDisplayBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.setup.SetupActivity
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayFragmentDirections.Companion.toMainActivity
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayState.Initialized
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayState.UserInformationSaved
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
        (activity as SetupActivity).setupComponent.inject(this)
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
            is UserInformationSaved -> onUserInformationSaved(state.userInformation)
        }
    }

    private fun onUserInformationSaved(userInformation: UserInformation) {
        (activity as SetupActivity).setUserInformation(userInformation)
        navigate(toMainActivity())
    }

    private fun displayGoal(goal: String) {
        binding.goalAmount.text = goal
    }
}
