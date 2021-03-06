package com.tsquaredapplications.liquid.presets.type

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
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.common.adapter.TypeItem
import com.tsquaredapplications.liquid.databinding.FragmentPresetTypeSelectionBinding
import com.tsquaredapplications.liquid.presets.add.AddPresetFragment.Companion.DRINK_TYPE_SELECTION_KEY
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.Initialized
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionState.TypeSelected
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PresetTypeSelectionFragment : BaseFragment<FragmentPresetTypeSelectionBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PresetTypeSelectionViewModel by viewModels { viewModelFactory }

    private val itemAdapter = ItemAdapter<TypeItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPresetTypeSelectionBinding =
        FragmentPresetTypeSelectionBinding.inflate(inflater, container, false)

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

    private fun onStateChange(state: PresetTypeSelectionState) {
        when (state) {
            is Initialized -> onInitialized(state)
            is TypeSelected -> onTypeSelected(state)
        }
    }

    private fun onInitialized(state: Initialized) {
        itemAdapter.add(state.typeItems)
    }

    private fun onTypeSelected(state: TypeSelected) {
        with(findNavController()) {
            previousBackStackEntry?.savedStateHandle?.set(
                DRINK_TYPE_SELECTION_KEY,
                state.drinkType
            )
            popBackStack()
        }
    }
}