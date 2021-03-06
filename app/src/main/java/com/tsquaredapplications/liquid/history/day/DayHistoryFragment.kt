package com.tsquaredapplications.liquid.history.day

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.tsquaredapplications.liquid.MainActivity
import com.tsquaredapplications.liquid.common.BaseFragment
import com.tsquaredapplications.liquid.databinding.FragmentDayHistoryBinding
import com.tsquaredapplications.liquid.ext.getEndTimeOfDayFor
import com.tsquaredapplications.liquid.ext.getStartOfDayFor
import com.tsquaredapplications.liquid.ext.navigate
import com.tsquaredapplications.liquid.history.day.DayHistoryState.Initialized
import com.tsquaredapplications.liquid.history.day.DayHistoryState.NoEntriesForDay
import com.tsquaredapplications.liquid.history.main.HistoryIconItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class DayHistoryFragment : BaseFragment<FragmentDayHistoryBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DayHistoryViewModel> { viewModelFactory }
    private val args by navArgs<DayHistoryFragmentArgs>()

    private val itemAdapter = ItemAdapter<HistoryIconItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDayHistoryBinding = FragmentDayHistoryBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewSetup()

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer {
            onStateChanged(it)
        })

        viewModel.start(args.timeRange)
    }

    private fun recyclerViewSetup() {
        with(binding.recyclerView) {
            adapter = fastAdapter
            layoutManager = GridLayoutManager(context, 3)
        }

        fastAdapter.onClickListener = { _, adapter, item, _ ->
            navigate(
                DayHistoryFragmentDirections.toUpdateEntryFragment(
                    entry = item.model.entryDataWrapper,
                    isOnlyEntryForDay = adapter.adapterItemCount == 1
                )
            )
            false
        }
    }

    private fun onStateChanged(state: DayHistoryState?) {
        when (state) {
            is Initialized -> onInitialized(state)
            is NoEntriesForDay -> findNavController().popBackStack()
        }
    }

    private fun onInitialized(state: Initialized) {
        (activity as MainActivity).supportActionBar?.title = state.screenTitle

        with(itemAdapter) {
            clear()
            add(state.historyIconModels)
        }
    }
}

@Parcelize
class TimestampRange(val startTime: Long, val endTime: Long) : Parcelable {
    constructor(timestampForDay: Long) : this(
        getStartOfDayFor(timestampForDay),
        getEndTimeOfDayFor(timestampForDay)
    )
}