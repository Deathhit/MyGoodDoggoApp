package com.deathhit.data.thumbnail.module

import com.deathhit.data.thumbnail.data_source.ImageRemoteDataSource
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSource
import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import com.deathhit.data.thumbnail.repository.ThumbnailRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ThumbnailRepositoryModule {
    @Provides
    @Singleton
    internal fun provideThumbnailRepository(
        imageRemoteDataSource: ImageRemoteDataSource,
        thumbnailLocalDataSource: ThumbnailLocalDataSource
    ): ThumbnailRepository =
        ThumbnailRepositoryImp(imageRemoteDataSource, thumbnailLocalDataSource)
}