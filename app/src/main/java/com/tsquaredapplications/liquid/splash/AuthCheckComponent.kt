package com.tsquaredapplications.liquid.splash

import com.tsquaredapplications.liquid.di.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface AuthCheckComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthCheckComponent
    }

    fun inject(authCheckActivity: AuthCheckActivity)
    fun inject(splashScreenFragment: SplashScreenFragment)
}