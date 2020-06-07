package com.tsquaredapplications.liquid.presets.icon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.adapter.PRESET_ICON_SELECTION_ID
import com.tsquaredapplications.liquid.common.database.icons.PresetIcon
import com.tsquaredapplications.liquid.databinding.PresetIconSelectionItemBinding

class PresetIconItem(val presetIconModel: PresetIcon) :
    AbstractBindingItem<PresetIconSelectionItemBinding>() {

    override val type: Int
        get() = PRESET_ICON_SELECTION_ID

    override fun bindView(binding: PresetIconSelectionItemBinding, payloads: List<Any>) {
        val storageReference = Firebase.storage.reference.child(presetIconModel.iconPath)

        GlideApp.with(binding.icon.context)
            .load(storageReference)
            .fitCenter()
            .into(binding.icon)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): PresetIconSelectionItemBinding =
        PresetIconSelectionItemBinding.inflate(inflater, parent, false)
}