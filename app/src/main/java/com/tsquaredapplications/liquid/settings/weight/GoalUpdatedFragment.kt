package com.tsquaredapplications.liquid.settings.weight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentGoalDisplayBinding

class GoalUpdatedFragment : BaseFragment<FragmentGoalDisplayBinding>() {

    private val args by navArgs<GoalUpdatedFragmentArgs>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGoalDisplayBinding = FragmentGoalDisplayBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goalAmount.text = args.goal

        binding.finishButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}