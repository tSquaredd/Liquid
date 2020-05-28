package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapperImpl
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MainComponent::class])
class MainModule {

    @Provides
    fun provideHomeResourceWrapper(impl: HomeResourceWrapperImpl): HomeResourceWrapper = impl

}