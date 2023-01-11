package com.deathhit.data.thumbnail.data_source

import com.deathhit.core.dog_api.response.Image

interface ImageLocalDataSource {
    suspend fun getNextPageIndex(): Int?

    suspend fun insertImagePage(imageList: List<Image>, isRefreshing: Boolean, pageIndex: Int)
}