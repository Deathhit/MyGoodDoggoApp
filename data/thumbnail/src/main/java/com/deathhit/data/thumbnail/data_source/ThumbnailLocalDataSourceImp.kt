package com.deathhit.data.thumbnail.data_source

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.deathhit.core.database.AppDatabase
import com.deathhit.core.database.model.BreedEntity
import com.deathhit.core.database.model.BreedThumbnailRefEntity
import com.deathhit.core.database.model.RemoteKeyEntity
import com.deathhit.core.database.model.ThumbnailEntity
import kotlinx.coroutines.flow.Flow

internal class ThumbnailLocalDataSourceImp(private val appDatabase: AppDatabase) :
    ThumbnailLocalDataSource {
    companion object {
        private const val REMOTE_KEY_LABEL = "62c50cde2d404e13bad1f2128c29988f"
    }

    private val thumbnailDao = appDatabase.thumbnailDao()

    override suspend fun getNextThumbnailPageIndex(): Int? = with(appDatabase) {
        withTransaction {
            remoteKeyDao()
                .getByLabel(REMOTE_KEY_LABEL)
        }?.nextKey
    }

    override fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailEntity?> =
        thumbnailDao.getEntityById(thumbnailId)

    override fun getThumbnailPagingSource(): PagingSource<Int, ThumbnailEntity> =
        thumbnailDao.getPagingSource()

    override suspend fun insertThumbnailPage(
        breedEntities: List<BreedEntity>,
        breedThumbnailRefEntities: List<BreedThumbnailRefEntity>,
        isRefreshing: Boolean,
        pageIndex: Int,
        thumbnailEntities: List<ThumbnailEntity>
    ) = with(appDatabase) {
        withTransaction {
            if (isRefreshing) {
                breedDao().clearAll()
                breedThumbnailRefDao().clearAll()
                thumbnailDao().clearAll()
                remoteKeyDao().clearAll()
            }

            // Update RemoteKey for this query.
            remoteKeyDao().insertOrReplace(
                RemoteKeyEntity(REMOTE_KEY_LABEL, pageIndex + 1)
            )

            // Insert the new data into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            breedThumbnailRefDao().insertOrReplaceAll(breedThumbnailRefEntities)
            breedDao().insertOrReplaceAll(breedEntities)
            thumbnailDao().insertOrReplaceAll(thumbnailEntities)
        }
    }
}