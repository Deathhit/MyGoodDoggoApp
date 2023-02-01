package com.deathhit.data.breed

import com.deathhit.core.database.AppDatabase
import com.deathhit.core.database.model.BreedEntity
import com.deathhit.core.database.model.BreedThumbnailRefEntity
import com.deathhit.data.breed.repository.BreedRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
internal class BreedRepositoryTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var appDatabase: AppDatabase

    @Inject
    internal lateinit var breedRepository: BreedRepository

    @Before
    fun before() {
        hiltRule.inject()
    }

    @After
    fun after() {
        appDatabase.close()
    }

    @Test
    fun getBreedListFlowByThumbnailIdReturnsCorrespondResultsFromDb() = runBlocking {
        //Given
        val fakeBreedList = listOf(
            BreedEntity("0", "0", "0", "0", "0", "0"),
            BreedEntity("1", "1", "1", "1", "1", "1"),
            BreedEntity("2", "2", "2", "2", "2", "2"),
            BreedEntity("3", "3", "3", "3", "3", "3"),
            BreedEntity("4", "4", "4", "4", "4", "4"),
            BreedEntity("5", "5", "5", "5", "5", "5"),
            BreedEntity("6", "6", "6", "6", "6", "6"),
            BreedEntity("7", "7", "7", "7", "7", "7"),
            BreedEntity("8", "8", "8", "8", "8", "8")
        )
        val fakeBreedRefList = listOf(
            BreedThumbnailRefEntity("0", "0"),
            BreedThumbnailRefEntity("1", "0"),
            BreedThumbnailRefEntity("2", "0"),
            BreedThumbnailRefEntity("3", "1"),
            BreedThumbnailRefEntity("4", "1"),
            BreedThumbnailRefEntity("5", "1"),
            BreedThumbnailRefEntity("6", "2"),
            BreedThumbnailRefEntity("7", "2"),
            BreedThumbnailRefEntity("8", "2")
        )

        with(appDatabase) {
            breedDao().insertOrReplaceAll(fakeBreedList)
            breedThumbnailRefDao().insertOrReplaceAll(fakeBreedRefList)
        }

        //When
        val resultOfId0 = breedRepository.getBreedListFlowByThumbnailId("0").first()
        val resultOfId1 = breedRepository.getBreedListFlowByThumbnailId("1").first()
        val resultOfId2 = breedRepository.getBreedListFlowByThumbnailId("2").first()

        //Then
        assert(fakeBreedList[0].breedId == resultOfId0[0].breedId && fakeBreedList[2].breedId == resultOfId0[2].breedId)
        assert(fakeBreedList[3].breedId == resultOfId1[0].breedId && fakeBreedList[5].breedId == resultOfId1[2].breedId)
        assert(fakeBreedList[6].breedId == resultOfId2[0].breedId && fakeBreedList[8].breedId == resultOfId2[2].breedId)
    }
}