package com.deathhit.data.breed.module

import com.deathhit.data.breed.data_source.BreedLocalDataSource
import com.deathhit.data.breed.repository.BreedRepository
import com.deathhit.data.breed.repository.BreedRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BreedRepositoryModule {
    @Provides
    @Singleton
    internal fun provideBreedRepository(breedLocalDataSource: BreedLocalDataSource): BreedRepository =
        BreedRepositoryImp(breedLocalDataSource)
}