package com.deathhit.data.thumbnail.repository

import androidx.paging.PagingData
import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.data.thumbnail.data_source.ThumbnailLocalDataSource
import kotlinx.coroutines.flow.Flow

internal class ThumbnailRepositoryImp(private val thumbnailLocalDataSource: ThumbnailLocalDataSource) :
    ThumbnailRepository {
    override fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailDO?> =
        thumbnailLocalDataSource.getThumbnailFlowById(thumbnailId)

    override fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>> =
        thumbnailLocalDataSource.getThumbnailListFlow()
}