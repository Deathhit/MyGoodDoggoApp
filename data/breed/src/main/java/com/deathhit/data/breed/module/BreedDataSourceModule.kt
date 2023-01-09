package com.deathhit.data.breed.module

import com.deathhit.core.database.AppDatabase
import com.deathhit.data.breed.data_source.BreedLocalDataSource
import com.deathhit.data.breed.data_source.BreedLocalDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object BreedDataSourceModule {
    @Provides
    @Singleton
    fun provideBreedLocalDataSource(appDatabase: AppDatabase): BreedLocalDataSource =
        BreedLocalDataSourceImp(appDatabase)
}