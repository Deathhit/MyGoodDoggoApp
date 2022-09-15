package com.deathhit.domain.use_case.thumbnail

import com.deathhit.data.repository.thumbnail.ThumbnailRepository

class GetThumbnailListFlowUseCase(private val thumbnailRepository: ThumbnailRepository) {
    operator fun invoke() = thumbnailRepository.getThumbnailListFlow()
}