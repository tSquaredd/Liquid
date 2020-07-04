package com.tsquaredapplications.liquid.history.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.R
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.adapter.HISTORY_DAY_ID
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.databinding.HistoryIconItemBinding

class HistoryIconItem(private val model: Model) : AbstractBindingItem<HistoryIconItemBinding>() {
    override val type: Int
        get() = HISTORY_DAY_ID

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): HistoryIconItemBinding =
        HistoryIconItemBinding.inflate(inflater, parent, false)

    override fun bindView(binding: HistoryIconItemBinding, payloads: List<Any>) {
        GlideApp.with(binding.icon.context)
            .load(model.icon.iconResource)
            .placeholder(R.drawable.drink_placeholder)
            .fitCenter()
            .into(binding.icon)
    }

    class Model(val icon: Icon)
}