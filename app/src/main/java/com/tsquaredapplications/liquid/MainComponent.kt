package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.add.amount.DrinkAmountFragment
import com.tsquaredapplications.liquid.add.drink.SelectDrinkFragment
import com.tsquaredapplications.liquid.di.ActivityScope
import com.tsquaredapplications.liquid.home.HomeFragment
import com.tsquaredapplications.liquid.presets.add.AddPresetFragment
import com.tsquaredapplications.liquid.presets.edit.EditPresetFragment
import com.tsquaredapplications.liquid.presets.icon.PresetIconSelectionFragment
import com.tsquaredapplications.liquid.presets.main.PresetsFragment
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionFragment
import com.tsquaredapplications.liquid.settings.SettingsFragment
import com.tsquaredapplications.liquid.settings.goal.GoalSettingFragment
import com.tsquaredapplications.liquid.settings.notifications.NotificationsSettingFragment
import com.tsquaredapplications.liquid.settings.unit.LiquidUnitSettingFragment
import com.tsquaredapplications.liquid.settings.weight.WeightSettingFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(addPresetFragment: AddPresetFragment)
    fun inject(presetTypeSelectionFragment: PresetTypeSelectionFragment)
    fun inject(presetIconSelectionFragment: PresetIconSelectionFragment)
    fun inject(presetFragment: PresetsFragment)
    fun inject(editPresetFragment: EditPresetFragment)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(goalSettingFragment: GoalSettingFragment)
    fun inject(selectDrinkFragment: SelectDrinkFragment)
    fun inject(drinkAmountFragment: DrinkAmountFragment)
    fun inject(liquidUnitSettingFragment: LiquidUnitSettingFragment)
    fun inject(weightSettingFragment: WeightSettingFragment)
    fun inject(notificationsSettingFragment: NotificationsSettingFragment)
}