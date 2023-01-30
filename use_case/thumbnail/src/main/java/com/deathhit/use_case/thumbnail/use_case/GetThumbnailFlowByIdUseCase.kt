package com.deathhit.use_case.thumbnail.use_case

import com.deathhit.data.thumbnail.ThumbnailDO
import kotlinx.coroutines.flow.Flow

interface GetThumbnailFlowByIdUseCase {
    operator fun invoke(thumbnailId: String): Flow<ThumbnailDO?>
}