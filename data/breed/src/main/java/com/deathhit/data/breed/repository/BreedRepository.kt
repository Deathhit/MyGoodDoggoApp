package com.deathhit.data.breed.repository

import com.deathhit.data.breed.BreedDO
import kotlinx.coroutines.flow.Flow

interface BreedRepository {
    fun getBreedListFlowByThumbnailId(thumbnailId: String): Flow<List<BreedDO>>
}