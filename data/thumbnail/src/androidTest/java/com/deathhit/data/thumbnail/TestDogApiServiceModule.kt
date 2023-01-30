package com.deathhit.data.thumbnail

import com.deathhit.core.dog_api.DogApiModule
import com.deathhit.core.dog_api.service.ImageApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DogApiModule::class]
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