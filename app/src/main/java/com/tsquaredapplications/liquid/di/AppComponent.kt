package com.tsquaredapplications.liquid.di

import android.app.Application
import android.content.Context
import com.tsquaredapplications.liquid.MainComponent
import com.tsquaredapplications.liquid.MainModule
import com.tsquaredapplications.liquid.login.LoginComponent
import com.tsquaredapplications.liquid.login.LoginModule
import com.tsquaredapplications.liquid.splash.AuthCheckComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        LoginModule::class,
        MainModule::class,
        ViewModelFactory.ViewModelModule::class]
)
interface AppComponent {
    fun authComponent(): AuthCheckComponent.Factory
    fun loginComponent(): LoginComponent.Factory
    fun mainComponent(): MainComponent.Factory

    fun context(): Context
    fun application(): Application
}