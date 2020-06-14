package com.tsquaredapplications.liquid.presets.icon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentPresetIconSelectionBinding
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.IconSelected
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.icon.adapter.PresetIconItem
import javax.inject.Inject

class PresetIconSelectionFragment : BaseFragment<FragmentPresetIconSelectionBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PresetIconSelectionViewModel by viewModels { viewModelFactory }

    private val itemAdapter = ItemAdapter<PresetIconItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPresetIconSelectionBinding =
        FragmentPresetIconSelectionBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSetup()

        with(viewModel) {
            stateLiveData.observe(viewLifecycleOwner, Observer {
                onStateChange(it)
            })
            start()
        }
    }

    private fun recyclerViewSetup() {
        with(binding.recyclerView) {
            adapter = fastAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        fastAdapter.onClickListener = { _, _, item, _ ->
            viewModel.onItemClick(item)
            false
        }
    }

    private fun onStateChange(state: PresetIconSelectionState) {
        when (state) {
            is Initialized -> onInitialized(state)
            is IconSelected -> onIconSelected(state)
        }
    }

    private fun onInitialized(state: Initialized) {
        itemAdapter.add(state.typeItems)
    }

    private fun onIconSelected(state: IconSelected) {
        with(findNavController()) {
            previousBackStackEntry?.savedStateHandle?.set(
                PRESET_ICON_SELECTION_KEY,
                state.icon
            )
            popBackStack()
        }
    }

    companion object {
        const val PRESET_ICON_SELECTION_KEY = "presetIconSelectionKey"
    }
}