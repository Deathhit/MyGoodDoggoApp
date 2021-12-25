package com.deathhit.domain.repository

import com.deathhit.domain.model.BreedDO

interface BreedRepository {
    suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO>
}