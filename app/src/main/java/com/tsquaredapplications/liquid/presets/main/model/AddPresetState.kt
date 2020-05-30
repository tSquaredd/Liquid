package com.tsquaredapplications.liquid.presets.main.model

import com.tsquaredapplications.liquid.login.LiquidUnit

sealed class AddPresetState {
    class Initialized(val unit: LiquidUnit) : AddPresetState()
}