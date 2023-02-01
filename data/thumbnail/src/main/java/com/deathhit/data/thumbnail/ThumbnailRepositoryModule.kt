package com.deathhit.data.thumbnail

import androidx.paging.ExperimentalPagingApi
import com.deathhit.data.thumbnail.data_source.ImageRemoteDataSource
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSource
import com.deathhit.data.thumbnail.repository.ThumbnailRemoteMediator
import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import com.deathhit.data.thumbnail.repository.ThumbnailRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object ThumbnailRepositoryModule {
    @Provides
    @Singleton
    internal fun provideThumbnailRemoteMediator(
        imageRemoteDataSource: ImageRemoteDataSource,
        thumbnailLocalDataSource: ThumbnailLocalDataSource
    ) = ThumbnailRemoteMediator(imageRemoteDataSource, thumbnailLocalDataSource)

    @Provides
    @Singleton
    internal fun provideThumbnailRepository(
        thumbnailLocalDataSource: ThumbnailLocalDataSource,
        thumbnailRemoteMediator: ThumbnailRemoteMediator
    ): ThumbnailRepository =
        ThumbnailRepositoryImp(thumbnailLocalDataSource, thumbnailRemoteMediator)
}