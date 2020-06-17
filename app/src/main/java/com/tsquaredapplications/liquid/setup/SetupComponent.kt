package com.tsquaredapplications.liquid.setup

import com.tsquaredapplications.liquid.di.ActivityScope
import com.tsquaredapplications.liquid.setup.goal.GoalDisplayFragment
import com.tsquaredapplications.liquid.setup.information.UserInformationFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SetupComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SetupComponent
    }

    fun inject(setupActivity: SetupActivity)
    fun inject(welcomeFragment: WelcomeFragment)
    fun inject(userInformationFragment: UserInformationFragment)
    fun inject(goalDisplayFragment: GoalDisplayFragment)
}