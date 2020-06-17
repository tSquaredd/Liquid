package com.tsquaredapplications.liquid.di

import android.app.Application
import android.content.Context
import com.tsquaredapplications.liquid.MainComponent
import com.tsquaredapplications.liquid.MainModule
import com.tsquaredapplications.liquid.setup.SetupComponent
import com.tsquaredapplications.liquid.setup.SetupModule
import com.tsquaredapplications.liquid.startup.StartupComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        SetupModule::class,
        MainModule::class,
        ViewModelFactory.ViewModelModule::class]
)
interface AppComponent {
    fun startupComponent(): StartupComponent.Factory
    fun setupComponent(): SetupComponent.Factory
    fun mainComponent(): MainComponent.Factory

    fun context(): Context
    fun application(): Application
}