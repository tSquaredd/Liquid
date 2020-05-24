package com.tsquaredapplications.liquid.di

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.common.auth.AuthManagerImpl
import com.tsquaredapplications.liquid.common.database.UserDatabaseManager
import com.tsquaredapplications.liquid.common.db.UserDatabaseManagerImpl
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
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesFirebaseDatabase(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideUserDatabaseManager(
        impl: UserDatabaseManagerImpl
    ): UserDatabaseManager = impl

    @Provides
    @Singleton
    fun provideAuthManager(
        impl: AuthManagerImpl
    ): AuthManager = impl
}