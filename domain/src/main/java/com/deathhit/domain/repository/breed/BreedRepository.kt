package com.deathhit.domain.repository.breed

import com.deathhit.domain.model.BreedDO

interface BreedRepository {
    suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO>
}