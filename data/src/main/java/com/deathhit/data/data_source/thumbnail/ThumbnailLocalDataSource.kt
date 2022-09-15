package com.deathhit.data.data_source.thumbnail

import androidx.paging.PagingData
import com.deathhit.core.model.ThumbnailDO
import kotlinx.coroutines.flow.Flow

interface ThumbnailLocalDataSource {
    fun getThumbnailListFlow() : Flow<PagingData<ThumbnailDO>>
}