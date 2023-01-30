package com.deathhit.use_case.breed

import com.deathhit.data.breed.repository.BreedRepository
import com.deathhit.use_case.breed.use_case.GetBreedListFlowByThumbnailIdUseCase
import com.deathhit.use_case.breed.use_case.GetBreedListFlowByThumbnailIdUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BreedUseCaseModule {
    @Provides
    internal fun provideGetBreedListFlowByThumbnailIdUseCase(breedRepository: BreedRepository): GetBreedListFlowByThumbnailIdUseCase =
        GetBreedListFlowByThumbnailIdUseCaseImp(breedRepository)
}