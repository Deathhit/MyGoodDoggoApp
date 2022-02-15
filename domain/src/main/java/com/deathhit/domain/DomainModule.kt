package com.deathhit.domain

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DomainModule {
    private const val DATABASE_FILE_NAME = "database_c30c94dbd10244aaa30fd3a7454bbd3c"

    @Provides
    fun provideDomainDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        DomainDatabase::class.java, DATABASE_FILE_NAME
    ).build()
}