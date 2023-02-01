package com.deathhit.use_case.thumbnail

import com.deathhit.data.thumbnail.repository.ThumbnailRepository

internal class GetThumbnailFlowByIdUseCaseImp(private val thumbnailRepository: ThumbnailRepository) :
    GetThumbnailFlowByIdUseCase {
    override operator fun invoke(thumbnailId: String) =
        thumbnailRepository.getThumbnailFlowById(thumbnailId)
}