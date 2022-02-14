package com.deathhit.domain

import com.deathhit.data_source_dog_api.ApiService
import com.deathhit.domain.repository.BreedRepository
import com.deathhit.domain.repository.BreedRepositoryImp
import com.deathhit.domain.repository.ThumbnailRepository
import com.deathhit.domain.repository.ThumbnailRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideBreedRepository(domainDatabase: DomainDatabase): BreedRepository =
        BreedRepositoryImp(domainDatabase)

    @Provides
    fun provideThumbnailRepository(
        apiService: ApiService,
        domainDatabase: DomainDatabase
    ): ThumbnailRepository = ThumbnailRepositoryImp(apiService, domainDatabase)
}