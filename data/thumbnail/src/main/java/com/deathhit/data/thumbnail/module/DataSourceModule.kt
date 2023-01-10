package com.deathhit.data.thumbnail.module

import com.deathhit.core.database.AppDatabase
import com.deathhit.core.dog_api.service.ImageApiService
import com.deathhit.data.thumbnail.data_source.*
import com.deathhit.data.thumbnail.data_source.ImageRemoteDataSourceImp
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSource
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSourceImp
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
    fun provideImageLocalDataSource(appDatabase: AppDatabase): ImageLocalDataSource =
        ImageLocalDataSourceImp(appDatabase)

    @Provides
    @Singleton
    fun provideImageRemoteDataSource(imageApiService: ImageApiService): ImageRemoteDataSource =
        ImageRemoteDataSourceImp(imageApiService)

    @Provides
    @Singleton
    fun provideThumbnailLocalDataSource(appDatabase: AppDatabase): ThumbnailLocalDataSource =
        ThumbnailLocalDataSourceImp(appDatabase)
}