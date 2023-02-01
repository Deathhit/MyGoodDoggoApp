package com.deathhit.use_case.breed

import com.deathhit.data.breed.BreedDO
import com.deathhit.use_case.breed.config.FakeBreedRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class BreedUseCaseTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var fakeBreedRepository: FakeBreedRepository

    @Inject
    internal lateinit var getBreedListFlowByThumbnailIdUseCase: GetBreedListFlowByThumbnailIdUseCase

    @Before
    fun before() {
        hiltRule.inject()

        fakeBreedRepository.breedListFlow.update { emptyList() }
    }

    @Test
    fun getBreedListFlowByThumbnailIdUseCaseReturnsTheCorrespondListInRepository() = runBlocking {
        //Given
        val fakeBreedList = listOf(
            BreedDO("0", "", "", "", "", ""),
            BreedDO("1", "", "", "", "", ""),
            BreedDO("2", "", "", "", "", "")
        )
        fakeBreedRepository.breedListFlow.update { fakeBreedList }

        //When
        val result = getBreedListFlowByThumbnailIdUseCase("").first()

        //Then
        assert(fakeBreedList[0].breedId == result[0].breedId)
    }
}