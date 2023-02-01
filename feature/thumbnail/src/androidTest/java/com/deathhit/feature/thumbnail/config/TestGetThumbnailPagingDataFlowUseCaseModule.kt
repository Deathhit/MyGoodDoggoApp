package com.deathhit.feature.thumbnail.config

import com.deathhit.use_case.thumbnail.GetThumbnailPagingDataFlowUseCase
import com.deathhit.use_case.thumbnail.GetThumbnailPagingDataFlowUseCaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [GetThumbnailPagingDataFlowUseCaseModule::class]
)
object TestGetThumbnailPagingDataFlowUseCaseModule {
    @Provides
    @Singleton
    fun provideFakeGetThumbnailPagingDataFlowUseCase(): FakeGetThumbnailPagingDataFlowUseCase =
        FakeGetThumbnailPagingDataFlowUseCase()

    @Provides
    @Singleton
    fun provideGetThumbnailPagingDataUseCase(fakeGetThumbnailPagingDataFlowUseCase: FakeGetThumbnailPagingDataFlowUseCase): GetThumbnailPagingDataFlowUseCase =
        fakeGetThumbnailPagingDataFlowUseCase
}