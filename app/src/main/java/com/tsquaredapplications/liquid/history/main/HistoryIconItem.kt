package com.tsquaredapplications.liquid.history.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.LiquidUnit
import com.tsquaredapplications.liquid.common.adapter.HISTORY_DAY_ID
import com.tsquaredapplications.liquid.common.database.entry.EntryDataWrapper
import com.tsquaredapplications.liquid.databinding.HistoryIconItemBinding
import com.tsquaredapplications.liquid.ext.setAsVisible
import com.tsquaredapplications.liquid.ext.toTwoDigitDecimalString

class HistoryIconItem(val model: Model) : AbstractBindingItem<HistoryIconItemBinding>() {
    override val type: Int
        get() = HISTORY_DAY_ID

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): HistoryIconItemBinding =
        HistoryIconItemBinding.inflate(inflater, parent, false)

    override fun bindView(binding: HistoryIconItemBinding, payloads: List<Any>) {
        if (model.detailed) {
            with(binding.name) {
                text = model.entryDataWrapper.preset?.name ?: model.entryDataWrapper.drinkType.name
                setAsVisible()
            }

            with(binding.amount) {
                val amountString =
                    "${model.entryDataWrapper.entry.amount.toTwoDigitDecimalString()} ${model.liquidUnit}"
                text = amountString
                setAsVisible()
            }
            binding.amount.setAsVisible()
        }

        GlideApp.with(binding.icon.context)
            .load(model.entryDataWrapper.icon.iconResource)
            .placeholder(R.drawable.drink_placeholder)
            .fitCenter()
            .into(binding.icon)
    }

    class Model(
        val entryDataWrapper: EntryDataWrapper,
        val liquidUnit: LiquidUnit,
        val detailed: Boolean = false
    )
}