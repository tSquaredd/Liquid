package com.tsquaredapplications.liquid.history.main

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
import com.tsquaredapplications.liquid.databinding.FragmentHistoryBinding
import com.tsquaredapplications.liquid.history.main.HistoryState.Initialized
import javax.inject.Inject

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

    override fun onAttach(context: Context) {
        (activity as MainActivity).mainComponent.inject(this)
        super.onAttach(context)
    }

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
            TODO("Show empty list")
        } else {
            itemAdapter.clear()
            itemAdapter.add(state.historyItems)
        }
    }

    private fun recyclerViewSetup() {
        with(binding.recyclerView) {
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
        }

        fastAdapter.onClickListener = { _, _, item, _ ->
            TODO()
            false
        }
    }
}