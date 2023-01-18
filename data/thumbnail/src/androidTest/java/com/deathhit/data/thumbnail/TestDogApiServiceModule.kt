package com.deathhit.data.thumbnail

import com.deathhit.core.dog_api.module.DogApiServiceModule
import com.deathhit.core.dog_api.service.ImageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DogApiServiceModule::class]
)
object TestDogApiServiceModule {
    @Provides
    @Singleton
    fun provideImageApiService(fakeImageApiService: FakeImageApiService): ImageApiService =
        fakeImageApiService

    @Provides
    @Singleton
    fun provideFakeImageApiService(): FakeImageApiService = FakeImageApiService()
}