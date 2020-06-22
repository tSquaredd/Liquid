package com.tsquaredapplications.liquid.setup.information

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentUserInformationBinding
import com.tsquaredapplications.liquid.ext.hideKeyboardFrom
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.setup.LiquidUnit
import com.tsquaredapplications.liquid.setup.SetupActivity
import com.tsquaredapplications.liquid.setup.information.UserInformationFragmentDirections.Companion.toDailyGoalDisplayFragment
import javax.inject.Inject

class UserInformationFragment : BaseFragment<FragmentUserInformationBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: UserInformationViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserInformationBinding =
        FragmentUserInformationBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as SetupActivity).setupComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.unitStateLiveData.observe(viewLifecycleOwner, Observer {
            onUnitChoiceStateChanged(it)
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        binding.ozButton.setOnClickListener {
            viewModel.onUnitSelected(LiquidUnit.OZ)
        }

        binding.mlButton.setOnClickListener {
            viewModel.onUnitSelected(LiquidUnit.ML)
        }

        binding.continueButton.setOnClickListener {
            viewModel.onContinueClicked(binding.weightEditText.text.toString())
        }

        binding.weightEditText.setOnFocusChangeListener { weightEditText, hasFocus ->
            if (hasFocus) {
                (weightEditText as TextInputEditText).hint = ""
            } else {
                (weightEditText as TextInputEditText).hint = getString(R.string.weight_input_hint)
                weightEditText.context.hideKeyboardFrom(view.windowToken)
            }
        }

        viewModel.start()
    }

    private fun onStateChanged(state: UserInformationState) {
        when (state) {
            is UserInformationState.InvalidWeight -> {
                showWeightError(state.errorMessage)
            }
            is UserInformationState.Continue -> {
                navigate(toDailyGoalDisplayFragment(state.weight, state.unitChoiceState))
            }
        }
    }

    private fun showWeightError(errorMessage: String) {
        binding.weightTextInputLayout.error = errorMessage
        binding.weightEditText.hint = ""

        setWeightTextWatcher(errorMessage)
    }

    private fun setWeightTextWatcher(errorMessage: String) {
        binding.weightEditText.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.weightTextInputLayout.error = errorMessage
                binding.weightEditText.hint = ""
            } else {
                removeWeightError()
            }
        }
    }

    private fun removeWeightError() {
        binding.weightTextInputLayout.error = null
    }

    private fun onUnitChoiceStateChanged(state: LiquidUnit) {
        when (state) {
            LiquidUnit.OZ -> setOzChecked()
            LiquidUnit.ML -> setMlChecked()
        }
    }

    private fun setOzChecked() {
        binding.ozButton.isChecked = true
        binding.mlButton.isChecked = false
    }

    private fun setMlChecked() {
        binding.mlButton.isChecked = true
        binding.ozButton.isChecked = false
    }

}
