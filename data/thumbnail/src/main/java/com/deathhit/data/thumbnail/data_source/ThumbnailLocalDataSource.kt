package com.deathhit.data.thumbnail.data_source

import androidx.paging.PagingSource
import com.deathhit.core.database.model.BreedEntity
import com.deathhit.core.database.model.BreedThumbnailRefEntity
import com.deathhit.core.database.model.ThumbnailEntity
import kotlinx.coroutines.flow.Flow

interface ThumbnailLocalDataSource {
    suspend fun getNextThumbnailPageIndex(): Int?

    fun getThumbnailFlowById(thumbnailId: String): Flow<ThumbnailEntity?>

    fun getThumbnailPagingSource(): PagingSource<Int, ThumbnailEntity>

    suspend fun insertThumbnailPage(
        breedEntities: List<BreedEntity>,
        breedThumbnailRefEntities: List<BreedThumbnailRefEntity>,
        isRefreshing: Boolean,
        pageIndex: Int,
        thumbnailEntities: List<ThumbnailEntity>
    )
}