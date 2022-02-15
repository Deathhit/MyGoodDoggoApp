package com.deathhit.domain

import com.deathhit.data_source_dog_api.ServiceModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ServiceModule::class]
)
internal object TestServiceModule {
    @Provides
    fun provideApiService(): TestApiService = TestApiService()
}