package com.deathhit.domain.use_case.breed

import com.deathhit.data.repository.breed.BreedRepository

class GetBreedListByThumbnailIdUseCase(private val breedRepository: BreedRepository) {
    suspend operator fun invoke(thumbnailId: String) =
        breedRepository.getBreedListByThumbnailId(thumbnailId)
}