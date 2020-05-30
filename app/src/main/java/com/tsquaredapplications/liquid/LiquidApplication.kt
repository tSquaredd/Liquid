package com.tsquaredapplications.liquid

import android.app.Application
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.di.AppModule
import com.tsquaredapplications.liquid.di.DaggerAppComponent

class LiquidApplication : Application() {

    val mainModule = MainModule()

    val appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .mainModule(mainModule)
        .build()

    fun setUserInformation(userInformation: UserInformation) {
        mainModule.userInformation = userInformation
    }
}