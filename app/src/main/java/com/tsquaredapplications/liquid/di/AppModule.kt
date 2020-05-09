package com.tsquaredapplications.liquid.di

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun providesContext(): Context = application

    @Provides
    @Singleton
    fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesFirebaseApp(): FirebaseApp {
        return FirebaseApp.initializeApp(application)!!
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}