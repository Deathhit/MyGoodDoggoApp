package com.deathhit.data.data_source.breed

import com.deathhit.core.model.BreedDO
import com.deathhit.data.database.AppDatabase
import com.deathhit.data.database.model.BreedEntity

internal class BreedLocalDataSourceImp(private val appDatabase: AppDatabase) : BreedLocalDataSource {
    override suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO> =
        mutableListOf<BreedDO>().apply {
            appDatabase.breedDao().getListByThumbnailId(thumbnailId).forEach {
                add(it.toDO())
            }
        }

    private fun BreedEntity.toDO() =
        BreedDO(breedId, bredFor, breedGroup, breedName, lifespan, temperament)
}