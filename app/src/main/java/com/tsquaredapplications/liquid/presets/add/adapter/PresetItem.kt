package com.tsquaredapplications.liquid.presets.add.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.GlideApp
import com.tsquaredapplications.liquid.common.adapter.PRESET_ID
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.databinding.PresetItemBinding

class PresetItem(val preset: Preset) : AbstractBindingItem<PresetItemBinding>() {

    override val type: Int
        get() = PRESET_ID

    override fun bindView(binding: PresetItemBinding, payloads: List<Any>) {
        binding.name.text = preset.name
        binding.amount.text = preset.sizeInOz.toString() // TODO FIX WITH UNIT
        binding.drinkType.text = preset.drinkType.name

        val storageReference = Firebase.storage.reference.child(preset.icon.iconPath)

        GlideApp.with(binding.icon.context)
            .load(storageReference)
            .fitCenter()
            .into(binding.icon)
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): PresetItemBinding =
        PresetItemBinding.inflate(inflater, parent, false)
}