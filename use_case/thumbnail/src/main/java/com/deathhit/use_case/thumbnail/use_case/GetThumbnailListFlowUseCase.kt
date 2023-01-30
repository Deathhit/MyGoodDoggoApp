package com.deathhit.use_case.thumbnail.use_case

import androidx.paging.PagingData
import com.deathhit.data.thumbnail.ThumbnailDO
import kotlinx.coroutines.flow.Flow

interface GetThumbnailListFlowUseCase {
    operator fun invoke(): Flow<PagingData<ThumbnailDO>>
}