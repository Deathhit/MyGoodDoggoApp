package com.deathhit.data.thumbnail.data_source

import com.deathhit.core.dog_api.response.Image

internal interface ImageLocalDataSource {
    suspend fun getNextPage(): Int?

    suspend fun insertImagePage(imageList: List<Image>, isRefreshing: Boolean, page: Int)
}