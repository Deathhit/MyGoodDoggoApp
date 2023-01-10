package com.deathhit.data.thumbnail.data_source

import com.deathhit.core.dog_api.response.Image
import retrofit2.HttpException

internal interface ImageRemoteDataSource {
    @Throws(HttpException::class)
    suspend fun fetchImageListByPage(page: Int?, pageSize: Int): List<Image>
}