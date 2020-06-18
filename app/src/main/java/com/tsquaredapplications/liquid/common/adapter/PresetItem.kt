package com.tsquaredapplications.liquid.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import com.tsquaredapplications.liquid.databinding.SelectionItemBinding

class PresetItem(val presetDataWrapper: PresetDataWrapper) :
    AbstractBindingItem<SelectionItemBinding>() {
    override val type: Int
        get() = PRESET_SELECTION_ID

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): SelectionItemBinding =
        SelectionItemBinding.inflate(inflater, parent, false)

    override fun bindView(binding: SelectionItemBinding, payloads: List<Any>) {
        binding.name.text = presetDataWrapper.preset.name

        GlideApp.with(binding.icon.context)
            .load(presetDataWrapper.icon.iconResource)
            .fitCenter()
            .into(binding.icon)
    }
}