package com.deathhit.data.thumbnail.repository

import androidx.paging.PagingData
import com.deathhit.data.thumbnail.ThumbnailDO
import kotlinx.coroutines.flow.Flow

interface ThumbnailRepository {
    fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailDO?>

    fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>>
}