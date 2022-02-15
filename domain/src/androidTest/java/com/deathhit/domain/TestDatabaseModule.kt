package com.deathhit.domain

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
internal object TestDatabaseModule {
    @Provides
    fun provideDomainDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context.applicationContext, DomainDatabase::class.java).build()
}