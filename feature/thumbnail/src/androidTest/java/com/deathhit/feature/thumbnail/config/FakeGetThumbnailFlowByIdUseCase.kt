package com.deathhit.feature.thumbnail.config

import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.use_case.thumbnail.GetThumbnailFlowByIdUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGetThumbnailFlowByIdUseCase: GetThumbnailFlowByIdUseCase {
    val thumbnailFlow = MutableStateFlow<ThumbnailDO?>(null)

    override fun invoke(thumbnailId: String): Flow<ThumbnailDO?> = thumbnailFlow
}