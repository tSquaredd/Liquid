package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.common.database.types.Type
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapper
import com.tsquaredapplications.liquid.home.resources.HomeResourceWrapperImpl
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MainComponent::class])
class MainModule() {

    lateinit var userInformation: UserInformation
    var types: List<Type> = emptyList()

    @Provides
    fun provideHomeResourceWrapper(impl: HomeResourceWrapperImpl): HomeResourceWrapper = impl

    @Provides
    fun provideUserInformation(): UserInformation = userInformation

    @Provides
    fun provideTypes(): List<Type> = types

}