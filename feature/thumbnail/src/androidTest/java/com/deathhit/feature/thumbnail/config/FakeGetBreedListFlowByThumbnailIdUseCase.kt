package com.deathhit.feature.thumbnail.config

import com.deathhit.data.breed.BreedDO
import com.deathhit.use_case.breed.GetBreedListFlowByThumbnailIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGetBreedListFlowByThumbnailIdUseCase: GetBreedListFlowByThumbnailIdUseCase {
    val breedListFlow = MutableStateFlow(emptyList<BreedDO>())

    override fun invoke(thumbnailId: String): Flow<List<BreedDO>> = breedListFlow
}