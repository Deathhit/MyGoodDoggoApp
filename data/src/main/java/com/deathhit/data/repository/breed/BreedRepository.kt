package com.deathhit.data.repository.breed

import com.deathhit.core.model.BreedDO

interface BreedRepository {
    suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO>
}