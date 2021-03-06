package com.tsquaredapplications.liquid.presets.add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.adapter.PRESET_ID
import com.tsquaredapplications.liquid.common.database.presets.PresetDataWrapper
import com.tsquaredapplications.liquid.databinding.PresetItemBinding

class DetailedPresetItem(val presetDataWrapper: PresetDataWrapper, private val amountText: String) :
    AbstractBindingItem<PresetItemBinding>() {

    override val type: Int
        get() = PRESET_ID

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): PresetItemBinding =
        PresetItemBinding.inflate(inflater, parent, false)

    override fun bindView(binding: PresetItemBinding, payloads: List<Any>) {
        binding.name.text = presetDataWrapper.preset.name
        binding.amount.text = amountText
        binding.drinkType.text = presetDataWrapper.drinkType.name

        GlideApp.with(binding.icon.context)
            .load(presetDataWrapper.icon.iconResource)
            .fitCenter()
            .into(binding.icon)
    }
}