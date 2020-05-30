package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.common.UserInformation
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapperImpl
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MainComponent::class])
class MainModule() {

    lateinit var userInformation: UserInformation

    @Provides
    fun provideHomeResourceWrapper(impl: HomeResourceWrapperImpl): HomeResourceWrapper = impl

    @Provides
    fun provideUserInformation(): UserInformation = userInformation

}