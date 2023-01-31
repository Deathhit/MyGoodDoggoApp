package com.deathhit.feature.thumbnail.config

import com.deathhit.use_case.breed.GetBreedListFlowByThumbnailIdUseCaseModule
import com.deathhit.use_case.breed.GetBreedListFlowByThumbnailIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GetBreedListFlowByThumbnailIdUseCaseModule::class]
)
object TestGetBreedListFlowByThumbnailIdUseCaseModule {
    @Provides
    @Singleton
    fun provideFakeGetBreedListFlowByThumbnailIdUseCase(): FakeGetBreedListFlowByThumbnailIdUseCase =
        FakeGetBreedListFlowByThumbnailIdUseCase()

    @Provides
    @Singleton
    fun provideGetBreedListFlowByThumbnailIdUseCase(fakeGetBreedListFlowByThumbnailIdUseCase: FakeGetBreedListFlowByThumbnailIdUseCase): GetBreedListFlowByThumbnailIdUseCase =
        fakeGetBreedListFlowByThumbnailIdUseCase
}