package com.deathhit.use_case.thumbnail.config

import androidx.paging.PagingData
import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

class FakeThumbnailRepository : ThumbnailRepository {
    val thumbnailFlow = MutableStateFlow<ThumbnailDO?>(null)
    val thumbnailPagingDataFlow = MutableStateFlow(PagingData.empty<ThumbnailDO>())

    override fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailDO?> = thumbnailFlow

    override fun getThumbnailListFlow(): Flow<PagingData<ThumbnailDO>> = thumbnailPagingDataFlow
}