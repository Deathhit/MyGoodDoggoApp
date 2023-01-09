package com.deathhit.use_case.thumbnail

import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import javax.inject.Inject

class GetThumbnailFlowByIdUseCase @Inject constructor(private val thumbnailRepository: ThumbnailRepository) {
    operator fun invoke(thumbnailId: String) =
        thumbnailRepository.getThumbnailFlowById(thumbnailId)
}