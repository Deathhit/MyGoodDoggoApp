package com.deathhit.use_case.breed

import com.deathhit.data.breed.repository.BreedRepository
import javax.inject.Inject

class GetBreedListFlowByThumbnailIdUseCase @Inject constructor(private val breedRepository: BreedRepository) {
    operator fun invoke(thumbnailId: String) =
        breedRepository.getBreedListFlowByThumbnailId(thumbnailId)
}