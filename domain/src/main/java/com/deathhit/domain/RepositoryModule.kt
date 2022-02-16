package com.deathhit.domain

import com.deathhit.data_source_dog_api.ImageApiService
import com.deathhit.domain.repository.BreedRepository
import com.deathhit.domain.repository.BreedRepositoryImp
import com.deathhit.domain.repository.ThumbnailRepository
import com.deathhit.domain.repository.ThumbnailRepositoryImp
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