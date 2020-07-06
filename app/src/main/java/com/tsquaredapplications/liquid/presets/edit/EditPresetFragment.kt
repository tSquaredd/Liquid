package com.tsquaredapplications.liquid.presets.edit

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
import androidx.navigation.fragment.navArgs
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.DoubleConfirmDialog
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.databinding.FragmentEditPresetBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.presets.edit.EditPresetFragmentDirections.Companion.toPresetIconSelectionFragment
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.AmountInvalid
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Deleted
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.IconUpdated
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Initialized
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.NameInvalid
import com.tsquaredapplications.liquid.presets.edit.EditPresetState.Updated
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionFragment.Companion.PRESET_ICON_SELECTION_KEY
import javax.inject.Inject

class EditPresetFragment : BaseFragment<FragmentEditPresetBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<EditPresetViewModel> { viewModelFactory }
    private val args by navArgs<EditPresetFragmentArgs>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditPresetBinding = FragmentEditPresetBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteButton.setOnClickListener {
            DoubleConfirmDialog(
                warningText = getString(R.string.delete_preset_warning),
                onConfirm = {
                    viewModel.deletePreset()
                }
            ).show(parentFragmentManager, null)
        }

        binding.presetIcon.setOnClickListener { navigate(toPresetIconSelectionFragment()) }

        binding.amountSelectionEditText.addTextChangedListener {
            it?.let {
                viewModel.onAmountChanged(it.toString())
                binding.amountSelectionTextLayout.error = ""
            }
        }

        binding.nameEditText.addTextChangedListener {
            it?.let {
                viewModel.onNameChanged(it.toString())
                binding.nameTextLayout.error = ""
            }
        }

        binding.updateButton.setOnClickListener {
            viewModel.updatePreset()
        }

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChange(it)
        })

        findNavController().currentBackStackEntry?.savedStateHandle?.let { savedStateHandle ->
            savedStateHandle.getLiveData<Icon>(PRESET_ICON_SELECTION_KEY)
                .observe(viewLifecycleOwner, Observer { selectedPresetIcon ->
                    selectedPresetIcon?.let { viewModel.presetIconSelected(it) }
                })
        }

        if (savedInstanceState == null) {
            viewModel.start(args.selectedPreset)
        }
    }

    private fun onStateChange(state: EditPresetState) {
        when (state) {
            is Initialized -> onInitializedState(state)
            is IconUpdated -> onIconUpdated(state)
            is Updated -> findNavController().popBackStack()
            is Deleted -> findNavController().popBackStack()
            is AmountInvalid -> onAmountInvalid(state.errorMessage)
            is NameInvalid -> onNameInvalid(state.errorMessage)
        }
    }

    private fun onInitializedState(state: Initialized) {
        binding.amountSelectionTextLayout.hint = state.amountUnitHint
        binding.amountSelectionEditText.text?.let {
            it.clear()
            it.insert(0, state.amountText)
        }

        binding.nameEditText.text?.let {
            it.clear()
            it.insert(0, state.name)
        }

        GlideApp.with(binding.presetIcon)
            .load(state.iconResource)
            .fitCenter()
            .into(binding.presetIcon)
    }

    private fun onIconUpdated(state: IconUpdated) {
        GlideApp.with(binding.presetIcon)
            .load(state.iconResource)
            .fitCenter()
            .into(binding.presetIcon)
    }

    private fun onAmountInvalid(errorMessage: String) {
        binding.amountSelectionTextLayout.error = errorMessage
    }

    private fun onNameInvalid(errorMessage: String) {
        binding.nameTextLayout.error = errorMessage
    }
}