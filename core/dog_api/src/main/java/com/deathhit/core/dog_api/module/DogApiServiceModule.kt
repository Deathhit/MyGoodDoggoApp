package com.deathhit.core.dog_api.module

import com.deathhit.core.dog_api.service.ImageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogApiServiceModule {
    @Provides
    @Singleton
    fun provideImageApiService(@DogApiRetrofitModule.DogApiRetrofit retrofit: Retrofit): ImageApiService =
        retrofit.create(ImageApiService::class.java)
}