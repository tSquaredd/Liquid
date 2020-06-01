package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.di.ActivityScope
import com.tsquaredapplications.liquid.home.HomeFragment
import com.tsquaredapplications.liquid.presets.main.AddPresetFragment
import com.tsquaredapplications.liquid.presets.type.PresetTypeSelectionFragment
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
}