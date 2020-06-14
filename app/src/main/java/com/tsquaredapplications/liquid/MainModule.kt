package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.common.database.icons.Icon
import com.tsquaredapplications.liquid.common.database.presets.Preset
import com.tsquaredapplications.liquid.common.database.types.DrinkType
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapperImpl
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapper
import com.tsquaredapplications.liquid.presets.add.resources.AddPresetResourceWrapperImpl
import com.tsquaredapplications.liquid.presets.edit.resources.EditPresetResourceWrapper
import com.tsquaredapplications.liquid.presets.edit.resources.EditPresetResourceWrapperImpl
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MainComponent::class])
class MainModule {

    lateinit var userInformation: UserInformation
    var drinkTypes: List<DrinkType> = emptyList()
    var presetIcons: List<Icon> = emptyList()
    private var presets: MutableList<Preset> = mutableListOf()

    fun initializePresets(presetsList: List<Preset>) {
        with(presets) {
            clear()
            addAll(presetsList)
        }
    }

    fun addPreset(preset: Preset) {
        with(presets) {
            add(preset)
            sortBy { it.name }
        }
    }

    fun updatePreset(preset: Preset) {
        val index = presets.indexOfFirst { it.dbKey == preset.dbKey }
        presets[index] = preset
    }

    fun deletePreset(preset: Preset) {
        presets.remove(preset)
    }

    @Provides
    fun provideUserInformation(): UserInformation = userInformation

    @Provides
    fun provideTypes(): List<DrinkType> = drinkTypes

    @Provides
    fun providePresetIcons(): List<Icon> = presetIcons

    @Provides
    fun providesPresets(): List<Preset> = presets

    @Provides
    fun provideHomeResourceWrapper(impl: HomeResourceWrapperImpl): HomeResourceWrapper = impl

    @Provides
    fun provideAddPresetResourceWrapper(impl: AddPresetResourceWrapperImpl)
            : AddPresetResourceWrapper = impl

    @Provides
    fun provideEditPresetResourceWrapper(impl: EditPresetResourceWrapperImpl)
            : EditPresetResourceWrapper = impl
}