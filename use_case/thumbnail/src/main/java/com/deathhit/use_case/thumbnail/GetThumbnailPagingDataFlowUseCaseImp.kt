package com.deathhit.use_case.thumbnail

import com.deathhit.data.thumbnail.repository.ThumbnailRepository

internal class GetThumbnailPagingDataFlowUseCaseImp(private val thumbnailRepository: ThumbnailRepository) :
    GetThumbnailPagingDataFlowUseCase {
    override operator fun invoke() = thumbnailRepository.getThumbnailListFlow()
}