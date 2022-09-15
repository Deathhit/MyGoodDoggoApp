package com.deathhit.data.module

import com.deathhit.data.data_source.breed.BreedLocalDataSource
import com.deathhit.data.data_source.thumbnail.ThumbnailLocalDataSource
import com.deathhit.data.repository.breed.BreedRepository
import com.deathhit.data.repository.breed.BreedRepositoryImp
import com.deathhit.data.repository.thumbnail.ThumbnailRepository
import com.deathhit.data.repository.thumbnail.ThumbnailRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun provideBreedRepository(breedLocalDataSource: BreedLocalDataSource): BreedRepository =
        BreedRepositoryImp(breedLocalDataSource)

    @Provides
    @Singleton
    fun provideThumbnailRepository(thumbnailLocalDataSource: ThumbnailLocalDataSource): ThumbnailRepository =
        ThumbnailRepositoryImp(thumbnailLocalDataSource)
}