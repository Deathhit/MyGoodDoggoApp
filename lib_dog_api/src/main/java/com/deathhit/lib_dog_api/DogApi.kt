package com.deathhit.lib_dog_api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogApi(apiKey: String, baseUrl: String) {
    companion object {
        private const val NAME_API_KEY = "x-api-key"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(OkHttpClient.Builder().addInterceptor {
            it.proceed(
                it.request().newBuilder().addHeader(
                    NAME_API_KEY,
                    apiKey
                ).build()
            )
        }.build()).build()

    val imageApiService: ImageApiService = retrofit.create(ImageApiService::class.java)
}