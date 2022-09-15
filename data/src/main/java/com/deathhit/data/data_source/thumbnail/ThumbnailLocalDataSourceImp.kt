package com.deathhit.data.data_source.thumbnail

import androidx.paging.*
import com.deathhit.core.model.ThumbnailDO
import com.deathhit.data.database.AppDatabase
import com.deathhit.data.database.model.ThumbnailEntity
import com.deathhit.lib_dog_api.ImageApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class ThumbnailLocalDataSourceImp(
    private val appDatabase: AppDatabase,
    private val imageApiService: ImageApiService
) : ThumbnailLocalDataSource {
    companion object {
        private const val PAGE_SIZE = 25
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>> = Pager(
        PagingConfig(PAGE_SIZE),
        null,
        ThumbnailRemoteMediator(appDatabase, imageApiService)
    ) {
        appDatabase.thumbnailDao().getPagingSource()
    }.flow.map { pagingData -> pagingData.map { it.toDO() } }

    private fun ThumbnailEntity.toDO() = ThumbnailDO(thumbnailId, thumbnailUrl)
}