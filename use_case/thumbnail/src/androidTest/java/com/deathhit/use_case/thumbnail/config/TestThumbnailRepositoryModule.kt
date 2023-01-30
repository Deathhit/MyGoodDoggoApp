package com.deathhit.use_case.thumbnail.config

import com.deathhit.data.thumbnail.ThumbnailRepositoryModule
import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ThumbnailRepositoryModule::class]
)
object TestThumbnailRepositoryModule {
    @Provides
    @Singleton
    fun provideBreedRepository(fakeThumbnailRepository: FakeThumbnailRepository): ThumbnailRepository =
        fakeThumbnailRepository

    @Provides
    @Singleton
    fun provideFakeBreedRepository(): FakeThumbnailRepository = FakeThumbnailRepository()
}