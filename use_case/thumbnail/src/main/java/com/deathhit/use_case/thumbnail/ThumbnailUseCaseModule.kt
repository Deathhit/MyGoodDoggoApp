package com.deathhit.use_case.thumbnail

import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import com.deathhit.use_case.thumbnail.use_case.GetThumbnailFlowByIdUseCase
import com.deathhit.use_case.thumbnail.use_case.GetThumbnailFlowByIdUseCaseImp
import com.deathhit.use_case.thumbnail.use_case.GetThumbnailListFlowUseCase
import com.deathhit.use_case.thumbnail.use_case.GetThumbnailListFlowUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ThumbnailUseCaseModule {
    @Provides
    internal fun provideGetThumbnailFlowByIdUseCase(thumbnailRepository: ThumbnailRepository): GetThumbnailFlowByIdUseCase =
        GetThumbnailFlowByIdUseCaseImp(thumbnailRepository)

    @Provides
    internal fun provideGetThumbnailListFlowUseCase(thumbnailRepository: ThumbnailRepository): GetThumbnailListFlowUseCase =
        GetThumbnailListFlowUseCaseImp(thumbnailRepository)
}