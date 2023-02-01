package com.deathhit.use_case.breed.config

import com.deathhit.data.breed.BreedRepositoryModule
import com.deathhit.data.breed.repository.BreedRepository
import com.deathhit.use_case.breed.config.FakeBreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BreedRepositoryModule::class]
)
object TestBreedRepositoryModule {
    @Provides
    @Singleton
    fun provideBreedRepository(fakeBreedRepository: FakeBreedRepository): BreedRepository =
        fakeBreedRepository

    @Provides
    @Singleton
    fun provideFakeBreedRepository(): FakeBreedRepository = FakeBreedRepository()
}