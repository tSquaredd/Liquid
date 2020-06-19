package com.tsquaredapplications.liquid.presets.main

import com.tsquaredapplications.liquid.presets.add.adapter.DetailedPresetItem

sealed class PresetState {
    class Initialized(val detailedPresets: List<DetailedPresetItem>) : PresetState()
    class Refresh(val detailedPresets: List<DetailedPresetItem>) : PresetState()
}