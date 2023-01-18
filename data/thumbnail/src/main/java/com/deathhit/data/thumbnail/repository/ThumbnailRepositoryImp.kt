package com.deathhit.data.thumbnail.repository

import androidx.paging.*
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
internal class ThumbnailRepositoryImp(
    private val thumbnailLocalDataSource: ThumbnailLocalDataSource,
    private val thumbnailRemoteMediator: ThumbnailRemoteMediator
) : ThumbnailRepository {
    companion object {
        private const val PAGE_SIZE = 25
    }

    override fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailDO?> =
        thumbnailLocalDataSource.getThumbnailFlowById(thumbnailId).map { it?.toDO() }

    override fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>> = Pager(
        PagingConfig(PAGE_SIZE),
        null,
        thumbnailRemoteMediator
    ) {
        thumbnailLocalDataSource.getThumbnailPagingSource()
    }.flow.map { pagingData -> pagingData.map { it.toDO() } }

    private fun ThumbnailEntity.toDO() = ThumbnailDO(thumbnailId, thumbnailUrl)
}