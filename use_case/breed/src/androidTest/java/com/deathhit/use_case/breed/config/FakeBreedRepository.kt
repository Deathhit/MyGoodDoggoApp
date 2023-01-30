package com.deathhit.use_case.breed.config

import com.deathhit.data.breed.BreedDO
import com.deathhit.data.breed.repository.BreedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeBreedRepository : BreedRepository {
    val breedListFlow = MutableStateFlow(emptyList<BreedDO>())

    override fun getBreedListFlowByThumbnailId(thumbnailId: String): Flow<List<BreedDO>> =
        breedListFlow
}