package com.deathhit.data.repository.thumbnail

import androidx.paging.PagingData
import com.deathhit.core.model.ThumbnailDO
import com.deathhit.data.data_source.thumbnail.ThumbnailLocalDataSource
import kotlinx.coroutines.flow.Flow

internal class ThumbnailRepositoryImp(private val thumbnailLocalDataSource: ThumbnailLocalDataSource) :
    ThumbnailRepository {
    override fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>> =
        thumbnailLocalDataSource.getThumbnailListFlow()
}