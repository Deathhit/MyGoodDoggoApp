package com.deathhit.data_source_dog_api

import android.content.Context
import com.deathhit.environment.MetadataProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object TransportProvider {
    private const val NAME_API_KEY = "x-api-key"

    private val baseClient by lazy { OkHttpClient.Builder().build() }

    private val stringApiKey = R.string.data_source_dog_api_api_key
    private val stringBaseUrl = R.string.data_source_dog_api_base_url

    fun createRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MetadataProvider.getMetadataString(context, context.getString(stringBaseUrl)))
            .client(createOkHttpClient(context))
            .build()
    }

    private fun createOkHttpClient(context: Context): OkHttpClient {
        val okHttpClientBuilder = baseClient.newBuilder()
        okHttpClientBuilder.addInterceptor {
            val request =
                it.request().newBuilder().addHeader(
                    NAME_API_KEY, MetadataProvider.getMetadataString(
                        context, context.getString(
                            stringApiKey
                        )
                    )
                ).build()
            it.proceed(request)
        }
        return okHttpClientBuilder.build()
    }
}