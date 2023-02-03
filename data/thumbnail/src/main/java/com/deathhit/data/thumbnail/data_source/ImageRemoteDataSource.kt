package com.deathhit.data.thumbnail.data_source

import com.deathhit.core.dog_api.model.Image
import com.deathhit.core.dog_api.service.ImageApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ImageRemoteDataSource @Inject constructor(private val imageApiService: ImageApiService) {
    suspend fun fetchImageByPage(page: Int?, pageSize: Int): List<Image> =
        imageApiService.searchImage(
            ImageApiService.SIZE_THUMB,
            true,
            ImageApiService.ORDER_DESC,
            page ?: ImageApiService.PAGE_DEFAULT,
            pageSize
        )
}