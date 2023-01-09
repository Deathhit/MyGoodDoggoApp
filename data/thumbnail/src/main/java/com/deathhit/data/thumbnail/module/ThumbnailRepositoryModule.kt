package com.deathhit.data.thumbnail.module

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
internal object ThumbnailRepositoryModule {
    @Provides
    @Singleton
    fun provideThumbnailRepository(thumbnailLocalDataSource: ThumbnailLocalDataSource): ThumbnailRepository =
        ThumbnailRepositoryImp(thumbnailLocalDataSource)
}