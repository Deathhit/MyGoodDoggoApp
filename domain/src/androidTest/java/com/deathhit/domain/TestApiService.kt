package com.deathhit.domain

import com.deathhit.data_source_dog_api.ApiService
import com.deathhit.data_source_dog_api.response.SearchImageResponse

internal class TestApiService : ApiService {
    val list = mutableListOf<SearchImageResponse>()

    override suspend fun searchImage(
        size: String?,
        hasBreeds: Boolean?,
        order: String?,
        page: Int?,
        limit: Int?
    ): List<SearchImageResponse> = list
}