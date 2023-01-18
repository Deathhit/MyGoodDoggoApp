package com.deathhit.use_case.thumbnail

import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import javax.inject.Inject

class GetThumbnailListFlowUseCase @Inject constructor(private val thumbnailRepository: ThumbnailRepository) {
    operator fun invoke() = thumbnailRepository.getThumbnailListFlow()
}