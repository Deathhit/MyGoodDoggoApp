package com.deathhit.feature.thumbnail.config

import com.deathhit.use_case.thumbnail.GetThumbnailFlowByIdUseCaseModule
import com.deathhit.use_case.thumbnail.GetThumbnailFlowByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GetThumbnailFlowByIdUseCaseModule::class]
)
object TestGetThumbnailFlowByIdUseCaseModule {
    @Provides
    @Singleton
    fun provideFakeGetThumbnailFlowByIdUseCase(): FakeGetThumbnailFlowByIdUseCase =
        FakeGetThumbnailFlowByIdUseCase()

    @Provides
    @Singleton
    fun provideGetBreedListFlowByThumbnailIdUseCase(fakeGetThumbnailFlowByIdUseCase: FakeGetThumbnailFlowByIdUseCase): GetThumbnailFlowByIdUseCase =
        fakeGetThumbnailFlowByIdUseCase
}