package com.deathhit.data.thumbnail.module

import com.deathhit.core.database.AppDatabase
import com.deathhit.core.dog_api.service.ImageApiService
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSource
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ThumbnailDataSourceModule {
    @Provides
    @Singleton
    fun provideThumbnailLocalDataSource(
        appDatabase: AppDatabase,
        imageApiService: ImageApiService
    ): ThumbnailLocalDataSource =
        ThumbnailLocalDataSourceImp(appDatabase, imageApiService)
}