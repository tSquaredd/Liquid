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
import com.tsquaredapplications.liquid.ext.setAsVisible
import com.tsquaredapplications.liquid.presets.add.adapter.DetailedPresetItem
import com.tsquaredapplications.liquid.presets.main.PresetState.Initialized
import com.tsquaredapplications.liquid.presets.main.PresetState.Refresh
import com.tsquaredapplications.liquid.presets.main.PresetsFragmentDirections.Companion.toAddPresetFragment
import com.tsquaredapplications.liquid.presets.main.PresetsFragmentDirections.Companion.toEditPresetFragment
import javax.inject.Inject

class PresetsFragment : BaseFragment<FragmentPresetsBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PresetsViewModel by viewModels { viewModelFactory }

    private val itemAdapter = ItemAdapter<DetailedPresetItem>()
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

        viewModel.getPresets()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    private fun recyclerViewSetup() {
        with(binding.presetsRecyclerView) {
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
        }

        fastAdapter.onClickListener = { _, _, item, _ ->
            navigate(toEditPresetFragment(item.presetDataWrapper))
            false
        }
    }

    private fun showPlaceholder() {
        binding.presetsRecyclerView.setAsGone()
        binding.emptyHeader.setAsVisible()
        binding.emptyImage.setAsVisible()
        binding.createPresetFab.setAsVisible()
        hideProgressBar()
    }

    private fun showPresets(detailedPresets: List<DetailedPresetItem>) {
        itemAdapter.clear()
        itemAdapter.add(detailedPresets)
        binding.emptyHeader.setAsGone()
        binding.emptyImage.setAsGone()
        binding.presetsRecyclerView.setAsVisible()
        binding.createPresetFab.setAsVisible()
        hideProgressBar()
    }

    private fun hideProgressBar() {
        binding.loadingMask.setAsGone()
        binding.progressBar.setAsGone()
    }


    private fun onStateChange(state: PresetState) {
        when (state) {
            is Initialized -> onInitializedState(state.detailedPresets)
            is Refresh -> onRefreshedState(state.detailedPresets)
        }
    }

    private fun onInitializedState(detailedPresets: List<DetailedPresetItem>) {
        if (detailedPresets.isEmpty()) {
            showPlaceholder()
        } else {
            showPresets(detailedPresets)
        }
    }

    private fun onRefreshedState(detailedPresets: List<DetailedPresetItem>) {
        itemAdapter.clear()
        if (detailedPresets.isNotEmpty()) {
            itemAdapter.add(detailedPresets)
            binding.emptyHeader.setAsGone()
            binding.emptyImage.setAsGone()
            binding.presetsRecyclerView.setAsVisible()
        } else {
            binding.presetsRecyclerView.setAsGone()
            binding.emptyHeader.setAsVisible()
            binding.emptyImage.setAsVisible()
        }
    }
}