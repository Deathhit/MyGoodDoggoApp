package com.deathhit.data.module

import android.content.Context
import com.deathhit.data.R
import com.deathhit.data.getMetadataString
import com.deathhit.lib_dog_api.DogApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DogApiModule {
    @Provides
    @Singleton
    fun provideDogApi(@ApplicationContext context: Context) = DogApi(
        getMetadataString(context, context.getString(R.string.data_dog_api_api_key)),
        getMetadataString(context, context.getString(R.string.data_dog_api_base_url))
    )
}