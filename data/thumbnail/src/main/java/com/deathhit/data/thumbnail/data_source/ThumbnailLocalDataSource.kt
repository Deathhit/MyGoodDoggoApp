package com.deathhit.data.thumbnail.data_source

import androidx.paging.PagingSource
import com.deathhit.core.database.model.ThumbnailEntity
import kotlinx.coroutines.flow.Flow

internal interface ThumbnailLocalDataSource {
    fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailEntity?>

    fun getThumbnailPagingSource(): PagingSource<Int, ThumbnailEntity>
}