package com.deathhit.feature.thumbnail.config

import androidx.paging.PagingData
import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.use_case.thumbnail.GetThumbnailPagingDataFlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGetThumbnailPagingDataFlowUseCase : GetThumbnailPagingDataFlowUseCase {
    val thumbnailPagingDataFlow = MutableStateFlow(PagingData.empty<ThumbnailDO>())

    override fun invoke(): Flow<PagingData<ThumbnailDO>> = thumbnailPagingDataFlow
}