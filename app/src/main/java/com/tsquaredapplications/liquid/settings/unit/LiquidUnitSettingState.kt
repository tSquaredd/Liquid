package com.tsquaredapplications.liquid.settings.unit

import com.tsquaredapplications.liquid.common.LiquidUnit

sealed class LiquidUnitSettingState {
    class Initialized(val unit: LiquidUnit) : LiquidUnitSettingState()
    class UnitSelected(val unit: LiquidUnit) : LiquidUnitSettingState()
    object OnUpdated : LiquidUnitSettingState()
}