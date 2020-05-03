package com.tsquaredapplications.liquid.di

import android.app.Application
import android.content.Context
import com.tsquaredapplications.liquid.login.LoginComponent
import com.tsquaredapplications.liquid.login.LoginModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, LoginModule::class, ViewModelFactory.ViewModelModule::class])
interface ApplicationComponent {
    fun loginComponent(): LoginComponent.Factory

    fun context(): Context
    fun application(): Application
}