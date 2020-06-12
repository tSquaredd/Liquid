package com.tsquaredapplications.liquid.presets.main

import com.tsquaredapplications.liquid.presets.add.adapter.PresetItem

sealed class PresetState {
    class Initialized(val presets: List<PresetItem>) : PresetState()
    class Refresh(val presets: List<PresetItem>) : PresetState()
}