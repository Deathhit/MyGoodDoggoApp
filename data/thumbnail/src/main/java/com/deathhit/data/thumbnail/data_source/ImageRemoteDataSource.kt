package com.deathhit.data.thumbnail.data_source

import com.deathhit.core.dog_api.response.Image

interface ImageRemoteDataSource {
    suspend fun fetchImageListByPage(page: Int?, pageSize: Int): List<Image>
}