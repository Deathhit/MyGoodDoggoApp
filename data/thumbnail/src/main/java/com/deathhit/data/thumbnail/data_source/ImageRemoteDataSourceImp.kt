package com.deathhit.data.thumbnail.data_source

import com.deathhit.core.dog_api.response.Image
import com.deathhit.core.dog_api.service.ImageApiService

internal class ImageRemoteDataSourceImp(private val imageApiService: ImageApiService) :
    ImageRemoteDataSource {
    override suspend fun fetchImageListByPage(page: Int?, pageSize: Int): List<Image> =
        imageApiService.searchImage(
            ImageApiService.SIZE_THUMB,
            true,
            ImageApiService.ORDER_DESC,
            page ?: ImageApiService.PAGE_DEFAULT,
            pageSize
        )


}