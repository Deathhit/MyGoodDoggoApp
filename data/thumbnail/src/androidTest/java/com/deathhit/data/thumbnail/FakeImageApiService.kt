package com.deathhit.data.thumbnail

import com.deathhit.core.dog_api.response.Image
import com.deathhit.core.dog_api.service.ImageApiService

class FakeImageApiService : ImageApiService {
    val imageList = mutableListOf<Image>()

    var isThrowingError = false

    override suspend fun searchImage(
        size: String?,
        hasBreeds: Boolean?,
        order: String?,
        page: Int?,
        limit: Int?
    ): List<Image> = if (isThrowingError) throw RuntimeException("isThrowingError = true") else imageList
}