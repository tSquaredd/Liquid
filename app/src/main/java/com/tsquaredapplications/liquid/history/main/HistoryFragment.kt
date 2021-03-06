package com.tsquaredapplications.liquid.history.main

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
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentHistoryBinding
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.ext.setAsVisible
import com.tsquaredapplications.liquid.history.day.TimestampRange
import com.tsquaredapplications.liquid.history.main.HistoryFragmentDirections.Companion.toDayHistoryFragment
import com.tsquaredapplications.liquid.history.main.HistoryState.Initialized
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HistoryViewModel by viewModels { viewModelFactory }

    private val itemAdapter = ItemAdapter<HistoryDayItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewSetup()

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start()
    }

    private fun onStateChanged(state: HistoryState) {
        when (state) {
            is Initialized -> onInitialized(state)
        }
    }

    private fun onInitialized(state: Initialized) {
        if (state.historyItems.isEmpty()) {
            binding.emptyHeader.setAsVisible()
            binding.emptyImage.setAsVisible()
        } else {
            binding.recyclerView.setAsVisible()
            itemAdapter.clear()
            itemAdapter.add(state.historyItems.map { HistoryDayItem(it) })
        }
    }

    private fun recyclerViewSetup() {
        with(binding.recyclerView) {
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
        }

        fastAdapter.onClickListener = { _, _, item, _ ->
            navigate(toDayHistoryFragment(TimestampRange(item.model.entries[0].entry.timestamp)))
            false
        }
    }
}