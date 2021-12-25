package com.deathhit.domain.repository

import android.content.Context
import com.deathhit.domain.database.DomainDatabase
import com.deathhit.domain.model.BreedDO

internal class BreedRepositoryImp(context: Context) : BreedRepository {
    private val domainDatabase = DomainDatabase.getInstance(context)

    override suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO> =
        domainDatabase.breedDao.getListByThumbnailId(thumbnailId)
}