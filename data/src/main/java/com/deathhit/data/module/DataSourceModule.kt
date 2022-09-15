package com.deathhit.data.module

import com.deathhit.data.data_source.breed.BreedLocalDataSource
import com.deathhit.data.data_source.breed.BreedLocalDataSourceImp
import com.deathhit.data.data_source.thumbnail.ThumbnailLocalDataSource
import com.deathhit.data.data_source.thumbnail.ThumbnailLocalDataSourceImp
import com.deathhit.data.database.AppDatabase
import com.deathhit.lib_dog_api.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {
    @Provides
    @Singleton
    fun provideBreedLocalDataSource(appDatabase: AppDatabase): BreedLocalDataSource =
        BreedLocalDataSourceImp(appDatabase)

    @Provides
    @Singleton
    fun provideThumbnailLocalDataSource(
        appDatabase: AppDatabase,
        dogApi: DogApi
    ): ThumbnailLocalDataSource = ThumbnailLocalDataSourceImp(appDatabase, dogApi.imageApiService)
}