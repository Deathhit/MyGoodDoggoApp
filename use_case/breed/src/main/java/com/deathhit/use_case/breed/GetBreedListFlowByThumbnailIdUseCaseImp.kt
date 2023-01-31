package com.deathhit.use_case.breed

import com.deathhit.data.breed.repository.BreedRepository

internal class GetBreedListFlowByThumbnailIdUseCaseImp(private val breedRepository: BreedRepository) :
    GetBreedListFlowByThumbnailIdUseCase {
    override operator fun invoke(thumbnailId: String) =
        breedRepository.getBreedListFlowByThumbnailId(thumbnailId)
}