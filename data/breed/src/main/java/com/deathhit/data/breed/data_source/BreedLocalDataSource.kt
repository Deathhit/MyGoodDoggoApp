package com.deathhit.data.breed.data_source

import com.deathhit.data.breed.BreedDO
import kotlinx.coroutines.flow.Flow

internal interface BreedLocalDataSource {
    fun getBreedListFlowByThumbnailId(thumbnailId: String): Flow<List<BreedDO>>
}