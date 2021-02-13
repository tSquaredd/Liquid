package com.tsquaredapplications.liquid.setup

import com.tsquaredapplications.liquid.setup.goal.resources.GoalDisplayResourceWrapper
import com.tsquaredapplications.liquid.setup.goal.resources.GoalDisplayResourceWrapperImpl
import com.tsquaredapplications.liquid.setup.information.resources.UserInformationResourceWrapper
import com.tsquaredapplications.liquid.setup.information.resources.UserInformationResourceWrapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SetupModule {

    @Provides
    fun providesUserInformationResourceWrapper(impl: UserInformationResourceWrapperImpl)
            : UserInformationResourceWrapper = impl

    @Provides
    fun providesGoalDisplayResourceWrapper(impl: GoalDisplayResourceWrapperImpl)
            : GoalDisplayResourceWrapper = impl
}