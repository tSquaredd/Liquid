package com.tsquaredapplications.liquid.presets.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentPresetsBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsGone
import com.tsquaredapplications.liquid.ext.setAsVisibile
import com.tsquaredapplications.liquid.presets.add.adapter.PresetItem
import com.tsquaredapplications.liquid.presets.main.PresetState.Initialized
import com.tsquaredapplications.liquid.presets.main.PresetState.Refresh
import com.tsquaredapplications.liquid.presets.main.PresetsFragmentDirections.Companion.toAddPresetFragment
import com.tsquaredapplications.liquid.presets.main.PresetsFragmentDirections.Companion.toEditPresetFragment
import javax.inject.Inject

class PresetsFragment : BaseFragment<FragmentPresetsBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PresetsViewModel by viewModels { viewModelFactory }

    private val itemAdapter = ItemAdapter<PresetItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPresetsBinding = FragmentPresetsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewSetup()

        binding.createPresetFab.setOnClickListener {
            navigate(toAddPresetFragment())
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

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    private fun recyclerViewSetup() {
        with(binding.presetsRecyclerView) {
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
        }

        fastAdapter.onClickListener = { _, _, item, _ ->
            navigate(toEditPresetFragment(item.preset))
            false
        }
    }

    private fun onStateChange(state: PresetState) {
        when (state) {
            is Initialized -> onInitializedState(state.presets)
            is Refresh -> onRefreshedState(state.presets)
        }
    }

    private fun onInitializedState(presets: List<PresetItem>) {
        if (presets.isNotEmpty()) {
            itemAdapter.add(presets)
            binding.emptyHeader.setAsGone()
            binding.emptyImage.setAsGone()
            binding.presetsRecyclerView.setAsVisibile()
        }
    }

    private fun onRefreshedState(presets: List<PresetItem>) {
        itemAdapter.clear()
        if (presets.isNotEmpty()) {
            itemAdapter.add(presets)
            binding.emptyHeader.setAsGone()
            binding.emptyImage.setAsGone()
            binding.presetsRecyclerView.setAsVisibile()
        } else {
            binding.presetsRecyclerView.setAsGone()
            binding.emptyHeader.setAsVisibile()
            binding.emptyImage.setAsVisibile()
        }
    }
}