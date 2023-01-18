package com.deathhit.core.dog_api.module

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.deathhit.core.dog_api.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DogApiRetrofitModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class DogApiRetrofit

    private const val NAME_API_KEY = "x-api-key"

    @DogApiRetrofit
    @Provides
    @Singleton
    fun provideDogApiRetrofit(@ApplicationContext context: Context): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(getBaseUrl(context))
        .client(OkHttpClient.Builder().addInterceptor {
            it.proceed(
                it.request().newBuilder().addHeader(
                    NAME_API_KEY,
                    getApiKey(context)
                ).build()
            )
        }.build()).build()

    private fun getApiKey(@ApplicationContext context: Context) =
        getMetadataString(context, context.getString(R.string.core_dog_api_api_key))

    private fun getBaseUrl(@ApplicationContext context: Context) =
        getMetadataString(context, context.getString(R.string.core_dog_api_base_url))

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