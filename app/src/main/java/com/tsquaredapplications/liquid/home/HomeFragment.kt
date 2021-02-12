package com.tsquaredapplications.liquid.home

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentHomeBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.home.HomeFragmentDirections.Companion.toSelectDrinkFragment
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState.Initialize
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState.SetProgress
import com.tsquaredapplications.liquid.home.HomeViewModel.HomeState.UpdateProgress
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private val args by navArgs<HomeFragmentArgs>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addDrinkButton.setOnClickListener {
            navigate(toSelectDrinkFragment())
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })

        viewModel.start(args.animateProgress)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    private fun onStateChange(state: HomeViewModel.HomeState) {
        when (state) {
            is Initialize -> initialize(state)
            is SetProgress -> setProgress(state)
            is UpdateProgress -> updateProgress(state)
        }
    }

    private fun initialize(state: Initialize) {
        binding.hydratingText.text = state.hydratingText
        binding.dehydratingText.text = state.dehydratingText
    }

    private fun setProgress(state: SetProgress) {
        if (binding.progressBar.progress > state.goalPercentage) {
            binding.progressBar.incrementProgressBy(
                -(binding.progressBar.progress - state.goalPercentage)
            )
        } else {
            binding.progressBar.setProgress(state.goalPercentage, false)
        }

        val goalText = "${state.goalPercentage} %"
        binding.progressText.text = goalText

        binding.hydratingAmount.text = state.hydratingAmount.toString()
        binding.dehydratingAmount.text = state.dehydratingAmount.toString()
    }

    private fun updateProgress(state: UpdateProgress) {
        animateProgressBar(
            previous = state.previousGoalPercentage,
            current = state.currentGoalPercentage
        )

        animateProgressText(
            previous = state.previousGoalPercentage,
            current = state.currentGoalPercentage
        )

        if (state.previousHydratingAmount == state.currentHydratingAmount) {
            binding.hydratingAmount.text = state.currentDehydratingAmount.toString()
        } else {
            animateNumberTextView(
                binding.hydratingAmount,
                state.previousHydratingAmount,
                state.currentHydratingAmount
            )
        }

        if (state.previousDehydratingAmount == state.currentDehydratingAmount) {
            binding.dehydratingAmount.text = state.currentDehydratingAmount.toString()
        } else {
            animateNumberTextView(
                binding.dehydratingAmount,
                state.previousDehydratingAmount,
                state.currentDehydratingAmount
            )
        }
    }

    private fun animateProgressBar(previous: Int, current: Int) {
        with(binding.progressBar) {
            setProgress(previous, false)
            with(ObjectAnimator.ofInt(binding.progressBar, "progress", previous, current)) {
                duration = 1000
                interpolator = LinearInterpolator()
                startDelay = 500
                start()
            }
        }
    }

    private fun animateProgressText(previous: Int, current: Int) {
        val startingProgressText = "$previous %"
        binding.progressText.text = startingProgressText
        with(
            ValueAnimator.ofInt(
                previous,
                current
            )
        ) {
            duration = 1000
            startDelay = 500
            addUpdateListener {
                val text = "${it.animatedValue} %"
                binding.progressText.text = text
            }
            start()
        }
    }

    private fun animateNumberTextView(textView: TextView, previous: Int, current: Int) {
        val startingText = "$previous"
        textView.text = startingText

        with(
            ValueAnimator.ofInt(
                previous,
                current
            )
        ) {
            duration = 1000
            startDelay = 500
            addUpdateListener {
                textView.text = it.animatedValue.toString()
            }
            start()
        }
    }
}