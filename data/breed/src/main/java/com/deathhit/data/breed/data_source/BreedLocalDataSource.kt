package com.deathhit.data.breed.data_source

import com.deathhit.core.database.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class BreedLocalDataSource @Inject constructor(private val appDatabase: AppDatabase) {
    fun getBreedListFlowByThumbnailId(thumbnailId: String) =
        appDatabase.breedDao().getListFlowByThumbnailId(thumbnailId)
}