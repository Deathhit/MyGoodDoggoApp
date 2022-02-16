package com.deathhit.domain.repository.breed

import com.deathhit.domain.DomainDatabase
import com.deathhit.domain.model.BreedDO
import javax.inject.Inject

internal class BreedRepositoryImp @Inject constructor(private val domainDatabase: DomainDatabase) :
    BreedRepository {
    override suspend fun getBreedListByThumbnailId(thumbnailId: String): List<BreedDO> =
        domainDatabase.breedDao().getListByThumbnailId(thumbnailId)
}