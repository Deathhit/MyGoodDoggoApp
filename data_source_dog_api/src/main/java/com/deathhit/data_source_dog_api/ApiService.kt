package com.deathhit.data_source_dog_api

import com.deathhit.data_source_dog_api.response.SearchImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val ORDER_DESC = "DESC"
        const val PAGE_DEFAULT = 0
        const val SIZE_THUMB = "thumb"
    }

    @GET("images/search")
    suspend fun searchImage(
        @Query("size") size: String?,
        @Query("has_breeds") hasBreeds: Boolean?,
        @Query("order") order: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): List<SearchImageResponse>
}