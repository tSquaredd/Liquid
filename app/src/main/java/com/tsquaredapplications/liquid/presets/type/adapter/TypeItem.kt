package com.tsquaredapplications.liquid.presets.type.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.adapter.PRESET_TYPE_SELECTION_ID
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeAndIcon
import com.tsquaredapplications.liquid.databinding.PresetTypeSelectionItemBinding

class TypeItem(val drinkTypeAndIcon: DrinkTypeAndIcon) :
    AbstractBindingItem<PresetTypeSelectionItemBinding>() {

    override val type: Int
        get() = PRESET_TYPE_SELECTION_ID

    override fun bindView(binding: PresetTypeSelectionItemBinding, payloads: List<Any>) {
        binding.name.text = drinkTypeAndIcon.drinkType.name

        GlideApp.with(binding.icon.context)
            .load(drinkTypeAndIcon.icon.iconResource)
            .fitCenter()
            .into(binding.icon)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): PresetTypeSelectionItemBinding {
        return PresetTypeSelectionItemBinding.inflate(inflater, parent, false)
    }
}