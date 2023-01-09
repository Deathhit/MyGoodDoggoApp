package com.deathhit.core.dog_api.module

import com.deathhit.core.dog_api.service.ImageApiService
import com.deathhit.core.dog_api.response.Image
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.delay
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DogApiServiceModule::class]
)
internal object TestDogApiServiceModule {
    private val breedList = listOf(
        Image.Breed(
            Image.Breed.Weight("", ""),
            Image.Breed.Height("", ""),
            "0",
            "0",
            "",
            "",
            "",
            "",
            ""
        ),
        Image.Breed(
            Image.Breed.Weight("", ""),
            Image.Breed.Height("", ""),
            "1",
            "1",
            "",
            "",
            "",
            "",
            ""
        ),
        Image.Breed(
            Image.Breed.Weight("", ""),
            Image.Breed.Height("", ""),
            "2",
            "2",
            "",
            "",
            "",
            "",
            ""
        )
    )

    private val imageList = listOf(
        Image(listOf(breedList[0]), "0", "", 0, 0),
        Image(listOf(breedList[1]), "1", "", 0, 0),
        Image(listOf(breedList[2]), "2", "", 0, 0)
    )

    @Provides
    @Singleton
    fun provideImageApiService() =  object : ImageApiService {
        override suspend fun searchImage(
            size: String?,
            hasBreeds: Boolean?,
            order: String?,
            page: Int?,
            limit: Int?
        ): List<Image> {
            delay(500)

            return imageList
        }
    }
}