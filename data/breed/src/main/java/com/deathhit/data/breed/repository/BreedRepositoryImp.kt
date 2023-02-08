package com.deathhit.data.breed.repository

import com.deathhit.data.breed.BreedDO
import com.deathhit.data.breed.data_source.BreedLocalDataSource
import com.deathhit.data.breed.toDO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BreedRepositoryImp(private val breedLocalDataSource: BreedLocalDataSource) :
    BreedRepository {
    override fun getBreedListFlowByThumbnailId(thumbnailId: String): Flow<List<BreedDO>> =
        breedLocalDataSource.getBreedListFlowByThumbnailId(thumbnailId)
            .map { it.map { breedEntity -> breedEntity.toDO() } }
}