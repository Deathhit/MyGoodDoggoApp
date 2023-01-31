package com.deathhit.use_case.thumbnail

import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GetThumbnailFlowByIdUseCaseModule {
    @Provides
    internal fun provideGetThumbnailFlowByIdUseCase(thumbnailRepository: ThumbnailRepository): GetThumbnailFlowByIdUseCase =
        GetThumbnailFlowByIdUseCaseImp(thumbnailRepository)
}