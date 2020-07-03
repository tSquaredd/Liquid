package com.tsquaredapplications.liquid

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.tsquaredapplications.liquid.di.AppComponent
import com.tsquaredapplications.liquid.di.AppModule
import com.tsquaredapplications.liquid.di.DaggerAppComponent

class LiquidApplication : Application() {

    val mainModule = MainModule()

    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .mainModule(mainModule)
        .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.notification_channel)
            val description =
                applicationContext.getString(R.string.notification_channel_description)
            val channel = NotificationChannel(
                CHANNEL_ID,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                this.description = description
            }

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "liquid_notifications"
    }
}