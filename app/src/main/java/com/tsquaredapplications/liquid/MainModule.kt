package com.tsquaredapplications.liquid

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

    @Provides
    fun provideUserInformation(): UserInformation = userInformation

    @Provides
    fun provideHomeResourceWrapper(impl: HomeResourceWrapperImpl): HomeResourceWrapper = impl

    @Provides
    fun provideAddPresetResourceWrapper(impl: AddPresetResourceWrapperImpl)
            : AddPresetResourceWrapper = impl

    @Provides
    fun provideEditPresetResourceWrapper(impl: EditPresetResourceWrapperImpl)
            : EditPresetResourceWrapper = impl
}