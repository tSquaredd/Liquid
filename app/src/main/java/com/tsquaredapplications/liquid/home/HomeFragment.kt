package com.tsquaredapplications.liquid.home

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
import com.tsquaredapplications.liquid.databinding.FragmentHomeBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.home.HomeFragmentDirections.Companion.toDrinkTypeFragment
import com.tsquaredapplications.liquid.home.model.HomeState
import com.tsquaredapplications.liquid.home.model.HomeState.Initialized
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addDrinkButton.setOnClickListener {
            navigate(toDrinkTypeFragment())
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })

        viewModel.start()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    private fun onStateChange(state: HomeState) {
        when (state) {
            is Initialized -> onInitialized(state)
        }
    }

    private fun onInitialized(state: Initialized) {
        binding.progressTextView.text = state.goalProgress
    }
}