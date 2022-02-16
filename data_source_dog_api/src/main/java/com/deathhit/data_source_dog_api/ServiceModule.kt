package com.deathhit.data_source_dog_api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideImageApiService(@NetworkModule.ApiRetrofit apiRetrofit: Retrofit): ImageApiService =
        apiRetrofit.create(ImageApiService::class.java)
}