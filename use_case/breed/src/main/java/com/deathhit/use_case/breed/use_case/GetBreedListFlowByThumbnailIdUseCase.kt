package com.deathhit.use_case.breed.use_case

import com.deathhit.data.breed.BreedDO
import kotlinx.coroutines.flow.Flow

interface GetBreedListFlowByThumbnailIdUseCase {
    operator fun invoke(thumbnailId: String): Flow<List<BreedDO>>
}