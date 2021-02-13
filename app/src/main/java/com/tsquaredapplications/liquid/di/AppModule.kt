package com.tsquaredapplications.liquid.di

import android.content.Context
import com.tsquaredapplications.liquid.common.database.AppDatabase
import com.tsquaredapplications.liquid.common.database.entry.EntryDao
import com.tsquaredapplications.liquid.common.database.entry.EntryRepository
import com.tsquaredapplications.liquid.common.database.entry.RoomEntryRepository
import com.tsquaredapplications.liquid.common.database.goal.GoalDao
import com.tsquaredapplications.liquid.common.database.goal.GoalRepository
import com.tsquaredapplications.liquid.common.database.goal.RoomGoalRepository
import com.tsquaredapplications.liquid.common.database.icons.IconDao
import com.tsquaredapplications.liquid.common.database.icons.IconRepository
import com.tsquaredapplications.liquid.common.database.icons.RoomIconRepository
import com.tsquaredapplications.liquid.common.database.presets.PresetDao
import com.tsquaredapplications.liquid.common.database.presets.PresetRepository
import com.tsquaredapplications.liquid.common.database.presets.RoomPresetRepository
import com.tsquaredapplications.liquid.common.database.types.DrinkTypeDao
import com.tsquaredapplications.liquid.common.database.types.RoomTypeRepository
import com.tsquaredapplications.liquid.common.database.types.TypeRepository
import com.tsquaredapplications.liquid.common.database.users.UserInformation
import com.tsquaredapplications.liquid.common.database.users.UserManager
import com.tsquaredapplications.liquid.common.database.users.UserManagerImpl
import com.tsquaredapplications.liquid.common.notifications.NotificationManager
import com.tsquaredapplications.liquid.common.notifications.NotificationManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

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
    fun provideEntryDao(appDatabase: AppDatabase): EntryDao = appDatabase.entryDao()

    @Provides
    @Singleton
    fun provideEntryRepository(impl: RoomEntryRepository): EntryRepository = impl

    @Provides
    @Singleton
    fun provideUserDatabaseManager(
        impl: UserManagerImpl
    ): UserManager = impl

    @Provides
    fun provideUserInformation(userManager: UserManager): UserInformation = userManager.getUser()

    @Provides
    @Singleton
    fun providesNotificationManager(
        impl: NotificationManagerImpl
    ): NotificationManager = impl

    @Provides
    @Singleton
    fun provideGoalDao(appDatabase: AppDatabase): GoalDao = appDatabase.goalDao()

    @Provides
    @Singleton
    fun provideGoalRepository(
        impl: RoomGoalRepository
    ): GoalRepository = impl
}