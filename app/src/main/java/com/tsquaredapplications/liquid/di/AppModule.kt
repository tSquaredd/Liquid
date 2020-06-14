package com.tsquaredapplications.liquid.di

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.tsquaredapplications.liquid.common.auth.AuthManager
import com.tsquaredapplications.liquid.common.auth.AuthManagerImpl
import com.tsquaredapplications.liquid.common.database.AppDatabase
import com.tsquaredapplications.liquid.common.database.icons.IconDao
import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import com.tsquaredapplications.liquid.common.database.icons.RoomIconRepository
import com.tsquaredapplications.liquid.common.database.presets.PresetDao
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.presets.RoomPresetRepository
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeDao
import com.tsquaredapplications.liquid.common.database.types.RoomTypeRepository
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import com.tsquaredapplications.liquid.common.database.users.UserDatabaseManager
import com.tsquaredapplications.liquid.common.database.users.UserDatabaseManagerImpl
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
    fun providesFirebaseDatabase(): FirebaseFirestore = Firebase.firestore // TODO REMOVE

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase = AppDatabase.getInstance(application)

    @Provides
    @Singleton
    fun provideIconDao(appDatabase: AppDatabase): IconDao = appDatabase.iconDao()

    @Provides
    @Singleton
    fun provideIconRepository(impl: RoomIconRepository): IconRepository = impl

    @Provides
    @Singleton
    fun provideDrinkTypeDao(appDatabase: AppDatabase): DrinkTypeDao = appDatabase.drinkTypeDao()

    @Provides
    @Singleton
    fun provideDrinkRepository(impl: RoomTypeRepository): TypeRepository = impl

    @Provides
    @Singleton
    fun providePresetDao(appDatabase: AppDatabase): PresetDao = appDatabase.presetDao()

    @Provides
    @Singleton
    fun providePresetRepository(impl: RoomPresetRepository): PresetRepository = impl

    @Provides
    @Singleton
    fun provideUserDatabaseManager(
        impl: UserDatabaseManagerImpl
    ): UserDatabaseManager = impl

    @Provides
    @Singleton
    fun providesFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton
    fun provideAuthManager(
        impl: AuthManagerImpl
    ): AuthManager = impl
}