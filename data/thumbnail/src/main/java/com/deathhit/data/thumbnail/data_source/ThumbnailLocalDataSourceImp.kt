package com.deathhit.data.thumbnail.data_source

import androidx.paging.*
import com.deathhit.core.database.AppDatabase
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.core.dog_api.service.ImageApiService
import com.deathhit.data.thumbnail.ThumbnailDO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ThumbnailLocalDataSourceImp(
    private val appDatabase: AppDatabase,
    private val imageApiService: ImageApiService
) : ThumbnailLocalDataSource {
    companion object {
        private const val PAGE_SIZE = 25
    }

    private val thumbnailDao = appDatabase.thumbnailDao()

    override fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailDO?> =
        thumbnailDao.getEntityById(thumbnailId).map { it?.toDO() }

    @OptIn(ExperimentalPagingApi::class)
    override fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>> = Pager(
        PagingConfig(PAGE_SIZE),
        null,
        ThumbnailRemoteMediator(appDatabase, imageApiService)
    ) {
        thumbnailDao.getPagingSource()
    }.flow.map { pagingData -> pagingData.map { it.toDO() } }

    private fun ThumbnailEntity.toDO() = ThumbnailDO(thumbnailId, thumbnailUrl)
}