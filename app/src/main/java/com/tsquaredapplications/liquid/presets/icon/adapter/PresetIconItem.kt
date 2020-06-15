package com.tsquaredapplications.liquid.presets.icon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.adapter.PRESET_ICON_SELECTION_ID
import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.databinding.PresetIconSelectionItemBinding

class PresetIconItem(val iconModel: Icon) :
    AbstractBindingItem<PresetIconSelectionItemBinding>() {

    override val type: Int
        get() = PRESET_ICON_SELECTION_ID

    override fun bindView(binding: PresetIconSelectionItemBinding, payloads: List<Any>) {
        GlideApp.with(binding.icon.context)
            .load(iconModel.iconResource)
            .fitCenter()
            .into(binding.icon)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): PresetIconSelectionItemBinding =
        PresetIconSelectionItemBinding.inflate(inflater, parent, false)
}