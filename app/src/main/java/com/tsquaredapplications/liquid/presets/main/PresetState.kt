package com.tsquaredapplications.liquid.presets.main

import com.tsquaredapplications.liquid.presets.add.adapter.DetailedPresetItem

sealed class PresetState {
    class ShowPresets(val detailedPresets: List<DetailedPresetItem>) : PresetState()
    object ShowPlaceholder : PresetState()
    class Refresh(val detailedPresets: List<DetailedPresetItem>) : PresetState()
}