package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapperImpl
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapper
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapperImpl
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MainComponent::class])
class MainModule {

    lateinit var userInformation: UserInformation
    var drinkTypes: List<DrinkType> = emptyList()
    var presetIcons: List<Icon> = emptyList()
    private var presets: MutableList<Preset> = mutableListOf()

    fun addPreset(preset: Preset) {
        with(presets) {
            add(preset)
            sortBy { it.name }
        }
    }

    fun initializePresets(presetsList: List<Preset>) {
        with(presets) {
            clear()
            addAll(presetsList)
        }
    }

    @Provides
    fun provideHomeResourceWrapper(impl: HomeResourceWrapperImpl): HomeResourceWrapper = impl

    @Provides
    fun provideAddPresetResourceWrapper(impl: AddPresetResourceWrapperImpl)
            : AddPresetResourceWrapper = impl

    @Provides
    fun provideUserInformation(): UserInformation = userInformation

    @Provides
    fun provideTypes(): List<DrinkType> = drinkTypes

    @Provides
    fun providePresetIcons(): List<Icon> = presetIcons

    @Provides
    fun providesPresets(): List<Preset> = presets

}