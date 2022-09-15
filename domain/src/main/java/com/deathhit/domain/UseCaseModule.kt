package com.deathhit.domain

import com.deathhit.data.repository.breed.BreedRepository
import com.deathhit.data.repository.thumbnail.ThumbnailRepository
import com.deathhit.domain.use_case.breed.GetBreedListByThumbnailIdUseCase
import com.deathhit.domain.use_case.thumbnail.GetThumbnailListFlowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {
    @Provides
    fun provideGetBreedListByThumbnailIdUseCase(breedRepository: BreedRepository) =
        GetBreedListByThumbnailIdUseCase(breedRepository)

    @Provides
    fun provideGetThumbnailListFlowUseCase(thumbnailRepository: ThumbnailRepository) =
        GetThumbnailListFlowUseCase(thumbnailRepository)
}