package com.deathhit.domain

import com.deathhit.data_source_dog_api.ImageApiService
import com.deathhit.data_source_dog_api.response.Image

internal class TestImageApiService : ImageApiService {
    val list = mutableListOf<Image>()

    override suspend fun searchImage(
        size: String?,
        hasBreeds: Boolean?,
        order: String?,
        page: Int?,
        limit: Int?
    ): List<Image> = list
}