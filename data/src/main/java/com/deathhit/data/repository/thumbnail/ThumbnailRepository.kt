package com.deathhit.data.repository.thumbnail

import androidx.paging.PagingData
import com.deathhit.core.model.ThumbnailDO
import kotlinx.coroutines.flow.Flow

interface ThumbnailRepository {
    fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>>
}