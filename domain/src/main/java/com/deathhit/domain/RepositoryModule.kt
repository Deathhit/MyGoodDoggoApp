package com.deathhit.domain

import com.deathhit.data_source_dog_api.ImageApiService
import com.deathhit.domain.repository.breed.BreedRepository
import com.deathhit.domain.repository.breed.BreedRepositoryImp
import com.deathhit.domain.repository.thumbnail.ThumbnailRepository
import com.deathhit.domain.repository.thumbnail.ThumbnailRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideBreedRepository(domainDatabase: DomainDatabase): BreedRepository =
        BreedRepositoryImp(domainDatabase)

    @Provides
    @Singleton
    fun provideThumbnailRepository(
        domainDatabase: DomainDatabase,
        imageApiService: ImageApiService
    ): ThumbnailRepository = ThumbnailRepositoryImp(domainDatabase, imageApiService)
}