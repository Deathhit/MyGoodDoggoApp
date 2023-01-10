package com.deathhit.data.thumbnail.data_source

import androidx.paging.PagingSource
import com.deathhit.core.database.AppDatabase
import com.deathhit.core.database.model.ThumbnailEntity
import kotlinx.coroutines.flow.Flow

internal class ThumbnailLocalDataSourceImp(appDatabase: AppDatabase) : ThumbnailLocalDataSource {
    private val thumbnailDao = appDatabase.thumbnailDao()

    override fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailEntity?> =
        thumbnailDao.getEntityById(thumbnailId)

    override fun getThumbnailPagingSource(): PagingSource<Int, ThumbnailEntity> =
        thumbnailDao.getPagingSource()
}