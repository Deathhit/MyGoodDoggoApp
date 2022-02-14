package com.deathhit.data_source_dog_api

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val NAME_API_KEY = "x-api-key"

    private val baseClient by lazy { OkHttpClient.Builder().build() }

    private val stringApiKey = R.string.data_source_dog_api_api_key
    private val stringBaseUrl = R.string.data_source_dog_api_base_url

    @Provides
    fun provideApiKeyInterceptor(@ApplicationContext context: Context): Interceptor = Interceptor {
        val request = it.request().newBuilder().addHeader(
            NAME_API_KEY,
            getMetadataString(context, context.getString(stringApiKey))
        ).build()
        it.proceed(request)
    }

    @Provides
    fun provideOkHttpClient(apiKeyInterceptor: Interceptor): OkHttpClient =
        baseClient.newBuilder().addInterceptor(apiKeyInterceptor).build()

    @Provides
    fun provideRetrofit(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(getMetadataString(context, context.getString(stringBaseUrl)))
            .client(okHttpClient)
            .build()
    }

    private fun getMetadataString(context: Context, key: String): String {
        var ai: ApplicationInfo? = null
        try {
            ai = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return ai!!.metaData!!.getString(key)!!
    }
}