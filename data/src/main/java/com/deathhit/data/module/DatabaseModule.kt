package com.deathhit.data.module

import android.content.Context
import androidx.room.Room
import com.deathhit.data.R
import com.deathhit.data.database.AppDatabase
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
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.create(context)
}