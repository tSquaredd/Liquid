package com.tsquaredapplications.liquid

import com.tsquaredapplications.liquid.di.ActivityScope
import com.tsquaredapplications.liquid.home.HomeFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)
}