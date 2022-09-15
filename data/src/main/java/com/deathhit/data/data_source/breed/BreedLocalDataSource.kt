package com.deathhit.data.data_source.breed

import com.deathhit.core.model.BreedDO

interface BreedLocalDataSource {
    suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO>
}