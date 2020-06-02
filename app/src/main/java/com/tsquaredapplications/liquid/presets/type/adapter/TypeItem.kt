package com.tsquaredapplications.liquid.presets.type.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.adapter.PRESET_TYPE_SELECTION_ID
import com.tsquaredapplications.liquid.common.database.types.Type
import com.tsquaredapplications.liquid.databinding.PresetTypeSelectionItemBinding

class TypeItem(val typeModel: Type) : AbstractBindingItem<PresetTypeSelectionItemBinding>() {

    override val type: Int
        get() = PRESET_TYPE_SELECTION_ID

    override fun bindView(binding: PresetTypeSelectionItemBinding, payloads: List<Any>) {
        binding.name.text = typeModel.name

        val storageReference = Firebase.storage.reference.child(typeModel.iconPath)

        GlideApp.with(binding.icon.context)
            .load(storageReference)
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