package com.tsquaredapplications.liquid.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.tsquaredapplications.liquid.common.adapter.SETTINGS_ID
import com.tsquaredapplications.liquid.databinding.SettingsItemBinding

class SettingsItem(val setting: Setting) : AbstractBindingItem<SettingsItemBinding>() {
    override val type: Int
        get() = SETTINGS_ID

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): SettingsItemBinding =
        SettingsItemBinding.inflate(inflater, parent, false)

    override fun bindView(binding: SettingsItemBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.name.text = setting.name
        binding.value.text = setting.value
    }
}