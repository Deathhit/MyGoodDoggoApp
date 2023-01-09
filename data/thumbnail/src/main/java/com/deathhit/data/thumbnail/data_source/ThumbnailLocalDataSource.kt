package com.deathhit.data.thumbnail.data_source

import androidx.paging.PagingData
import com.deathhit.data.thumbnail.ThumbnailDO
import kotlinx.coroutines.flow.Flow

internal interface ThumbnailLocalDataSource {
    fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailDO?>

    fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>>
}