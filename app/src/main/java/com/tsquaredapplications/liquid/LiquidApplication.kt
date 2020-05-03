package com.tsquaredapplications.liquid

import android.app.Application
import com.tsquaredapplications.liquid.di.AppModule
import com.tsquaredapplications.liquid.di.DaggerApplicationComponent

class LiquidApplication : Application() {
    val appComponent = DaggerApplicationComponent.builder()
        .appModule(AppModule(this))
        .build()
}