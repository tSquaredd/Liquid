package com.tsquaredapplications.liquid.presets.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.databinding.FragmentAddPresetBinding
import com.tsquaredapplications.liquid.ext.keyboardHidingFocusChangeListener
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.presets.add.AddPresetFragmentDirections.Companion.toAddPresetIconSelectionFragment
import com.tsquaredapplications.liquid.presets.add.AddPresetFragmentDirections.Companion.toAddPresetTypeSelectionFragment
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.DrinkTypeSelected
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.Initialized
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidAmount
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidDrinkType
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidIcon
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.InvalidName
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.PresetAdded
import com.tsquaredapplications.liquid.presets.add.model.AddPresetState.PresetIconSelected
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionFragment.Companion.PRESET_ICON_SELECTION_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddPresetFragment : BaseFragment<FragmentAddPresetBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AddPresetViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPresetBinding = FragmentAddPresetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.drinkIcon.setOnClickListener {
            navigate(toAddPresetIconSelectionFragment())
        }

        binding.addButton.setOnClickListener {
            viewModel.onAddClicked()
        }

        with(binding.nameEditText) {
            addTextChangedListener {
                it?.let {
                    viewModel.onNameChanged(it.toString())
                    binding.nameTextLayout.error = ""
                }
            }
            onFocusChangeListener = keyboardHidingFocusChangeListener
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

        with(binding.typeSelectionEditText) {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    navigate(toAddPresetTypeSelectionFragment())
                    binding.typeSelectionTextLayout.error = ""
                }
            }
            showSoftInputOnFocus = false
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer { state ->
            onStateChange(state)
        })

        findNavController().currentBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
            savedStateHandle.getLiveData<DrinkType>(DRINK_TYPE_SELECTION_KEY)
                .observe(viewLifecycleOwner, Observer { selectedDrinkType ->
                    selectedDrinkType?.let { viewModel.drinkTypeSelected(it) }
                })

            savedStateHandle.getLiveData<Icon>(PRESET_ICON_SELECTION_KEY)
                .observe(viewLifecycleOwner, Observer { selectedPresetIcon ->
                    selectedPresetIcon?.let { viewModel.presetIconSelected(it) }
                })
        }

        viewModel.start()
    }

    private fun onStateChange(state: AddPresetState) {
        when (state) {
            is Initialized -> onInitializedState(state)
            is DrinkTypeSelected -> onDrinkTypeSelected(state)
            is PresetIconSelected -> onPresetIconSelected(state)
            is InvalidName -> onInvalidName(state)
            is InvalidIcon -> onInvalidIcon()
            is InvalidDrinkType -> onInvalidType(state)
            is InvalidAmount -> onInvalidAmount(state)
            is PresetAdded -> onPresetAdded()
        }
    }

    private fun onInitializedState(state: Initialized) {
        binding.amountSelectionTextLayout.hint = state.unit.name
    }

    private fun onDrinkTypeSelected(state: DrinkTypeSelected) {
        binding.typeSelectionEditText.setText(state.drinkType.name)
    }

    private fun onPresetIconSelected(state: PresetIconSelected) {
        GlideApp.with(binding.drinkIcon.context)
            .load(state.icon.largeIconResource)
            .fitCenter()
            .into(binding.drinkIcon)

        binding.circularBackground.setBackgroundResource(R.drawable.type_background_with_border)
    }

    private fun onInvalidName(state: InvalidName) {
        binding.nameTextLayout.error = state.errorMessage
    }

    private fun onInvalidIcon() {
        binding.circularBackground.setBackgroundResource(R.drawable.preset_icon_error_background)
    }

    private fun onInvalidType(state: InvalidDrinkType) {
        binding.typeSelectionTextLayout.error = state.errorMessage
    }

    private fun onInvalidAmount(state: InvalidAmount) {
        binding.amountSelectionTextLayout.error = state.errorMessage
    }

    private fun onPresetAdded() {
        findNavController().popBackStack()
    }

    companion object {
        const val DRINK_TYPE_SELECTION_KEY = "drinkTypeSelectionKey"
    }
}