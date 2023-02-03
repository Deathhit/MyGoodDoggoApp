package com.deathhit.core.dog_api.service

import com.deathhit.core.dog_api.model.Image
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {
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
    ): List<Image>
}