package com.deathhit.use_case.breed

import com.deathhit.data.breed.repository.BreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetBreedListFlowByThumbnailIdUseCaseModule {
    @Provides
    internal fun provideGetBreedListFlowByThumbnailIdUseCase(breedRepository: BreedRepository): GetBreedListFlowByThumbnailIdUseCase =
        GetBreedListFlowByThumbnailIdUseCaseImp(breedRepository)
}