package com.tsquaredapplications.liquid.settings.unit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.databinding.FragmentLiquidUnitSettingBinding
import com.tsquaredapplications.liquid.ext.setAsVisible
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingState.Initialized
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingState.OnUpdated
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingState.UnitSelected
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LiquidUnitSettingFragment : BaseFragment<FragmentLiquidUnitSettingBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LiquidUnitSettingViewModel> { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLiquidUnitSettingBinding =
        FragmentLiquidUnitSettingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ozButton.setOnClickListener {
            viewModel.onUnitSelected(LiquidUnit.OZ)
        }

        binding.mlButton.setOnClickListener {
            viewModel.onUnitSelected(LiquidUnit.ML)
        }

        binding.updateButton.setOnClickListener {
            binding.progressBar.setAsVisible()
            binding.loadingMask.setAsVisible()
            viewModel.onUpdateClicked()
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })

        viewModel.start()
    }

    private fun onStateChange(state: LiquidUnitSettingState?) {
        when (state) {
            is Initialized -> updateUnitSelection(state.unit)
            is UnitSelected -> updateUnitSelection(state.unit)
            is OnUpdated -> onUpdateComplete()
        }
    }

    private fun updateUnitSelection(unit: LiquidUnit) {
        binding.ozButton.isChecked = unit == LiquidUnit.OZ
        binding.mlButton.isChecked = unit == LiquidUnit.ML
    }

    private fun onUpdateComplete() {
        findNavController().popBackStack()
    }

}