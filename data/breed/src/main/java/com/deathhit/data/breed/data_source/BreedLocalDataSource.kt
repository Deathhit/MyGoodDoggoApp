package com.deathhit.data.breed.data_source

import com.deathhit.core.database.AppDatabase
import com.deathhit.core.database.model.BreedEntity
import com.deathhit.data.breed.BreedDO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BreedLocalDataSource @Inject constructor(appDatabase: AppDatabase) {
    private val breedDao = appDatabase.breedDao()

    fun getBreedListFlowByThumbnailId(thumbnailId: String): Flow<List<BreedDO>> =
        breedDao.getListFlowByThumbnailId(thumbnailId)
            .map { it.map { breedEntity -> breedEntity.toDO() } }

    private fun BreedEntity.toDO() =
        BreedDO(breedId, bredFor, breedGroup, breedName, lifespan, temperament)
}