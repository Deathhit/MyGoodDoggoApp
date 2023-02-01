package com.deathhit.use_case.thumbnail

import com.deathhit.data.thumbnail.ThumbnailDO
import kotlinx.coroutines.flow.Flow

interface GetThumbnailFlowByIdUseCase {
    operator fun invoke(thumbnailId: String): Flow<ThumbnailDO?>
}