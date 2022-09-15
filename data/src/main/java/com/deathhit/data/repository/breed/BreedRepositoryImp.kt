package com.deathhit.data.repository.breed

import com.deathhit.core.model.BreedDO
import com.deathhit.data.data_source.breed.BreedLocalDataSource

internal class BreedRepositoryImp(private val breedLocalDataSource: BreedLocalDataSource) :
    BreedRepository {
    override suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO> =
        breedLocalDataSource.getBreedListByThumbnailId(thumbnailId)
}