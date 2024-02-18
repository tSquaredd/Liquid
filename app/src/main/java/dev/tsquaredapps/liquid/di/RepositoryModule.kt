package dev.tsquaredapps.liquid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tsquaredapps.liquid.data.AppDatabase
import dev.tsquaredapps.liquid.data.entry.EntryRepository
import dev.tsquaredapps.liquid.data.entry.RoomEntryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideEntryRepository(database: AppDatabase): EntryRepository =
        RoomEntryRepository(database.entryDao())
}