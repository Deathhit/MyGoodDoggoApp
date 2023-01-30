package com.deathhit.use_case.thumbnail.use_case

import com.deathhit.data.thumbnail.repository.ThumbnailRepository

internal class GetThumbnailListFlowUseCaseImp(private val thumbnailRepository: ThumbnailRepository) :
    GetThumbnailListFlowUseCase {
    override operator fun invoke() = thumbnailRepository.getThumbnailListFlow()
}