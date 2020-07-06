package com.tsquaredapplications.liquid.history.main

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.adapter.HISTORY_DAY_ID
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.databinding.HistoryItemBinding
import com.tsquaredapplications.liquid.setup.LiquidUnit
import kotlinx.android.parcel.Parcelize

class HistoryDayItem(val model: Model) : AbstractBindingItem<HistoryItemBinding>() {
    private val itemAdapter = ItemAdapter<HistoryIconItem>()
    private val fastAdapter = FastAdapter.with(itemAdapter)

    override val type: Int
        get() = HISTORY_DAY_ID

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): HistoryItemBinding =
        HistoryItemBinding.inflate(inflater, parent, false)

    override fun bindView(binding: HistoryItemBinding, payloads: List<Any>) {
        binding.date.text = model.date
        binding.progress.text = model.progress
        with(binding.recyclerView) {
            adapter = fastAdapter
            layoutManager = GridLayoutManager(binding.recyclerView.context, 4)
        }
        itemAdapter.clear()
        val historyIconItems = model.entries.map {
            HistoryIconItem(
                HistoryIconItem.Model(it, model.liquidUnit)
            )
        }

        itemAdapter.add(historyIconItems)
    }

    @Parcelize
    class Model(
        val date: String,
        val entries: List<EntryDataWrapper>,
        val progress: String,
        val liquidUnit: LiquidUnit
    ) : Parcelable
}