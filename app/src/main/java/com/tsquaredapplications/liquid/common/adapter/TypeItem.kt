package com.tsquaredapplications.liquid.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeAndIcon
import com.tsquaredapplications.liquid.databinding.SelectionItemBinding

class TypeItem(val drinkTypeAndIcon: DrinkTypeAndIcon) :
    AbstractBindingItem<SelectionItemBinding>() {

    override val type: Int
        get() = TYPE_SELECTION_ID

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): SelectionItemBinding {
        return SelectionItemBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: SelectionItemBinding, payloads: List<Any>) {
        binding.name.text = drinkTypeAndIcon.drinkType.name

        GlideApp.with(binding.icon.context)
            .load(drinkTypeAndIcon.icon.iconResource)
            .fitCenter()
            .into(binding.icon)
    }
}