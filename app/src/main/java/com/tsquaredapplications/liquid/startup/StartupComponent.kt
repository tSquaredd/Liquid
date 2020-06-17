package com.tsquaredapplications.liquid.startup

import com.tsquaredapplications.liquid.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface StartupComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): StartupComponent
    }

    fun inject(startupActivity: StartupActivity)
    fun inject(splashScreenFragment: SplashScreenFragment)
}