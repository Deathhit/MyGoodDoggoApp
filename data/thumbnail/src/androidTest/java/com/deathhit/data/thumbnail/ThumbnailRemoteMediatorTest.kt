package com.deathhit.data.thumbnail

import androidx.paging.*
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.core.dog_api.model.Image
import com.deathhit.data.thumbnail.config.FakeImageApiService
import com.deathhit.data.thumbnail.repository.ThumbnailRemoteMediator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
@HiltAndroidTest
class ThumbnailRemoteMediatorTest {
    companion object {
        private const val PAGE_SIZE = 25
    }

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var fakeImageApiService: FakeImageApiService

    @Inject
    internal lateinit var remoteMediator: ThumbnailRemoteMediator

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        //Given
        fakeImageApiService.isThrowingError = true

        //When
        val pagingState = PagingState<Int, ThumbnailEntity>(
            listOf(),
            null,
            PagingConfig(PAGE_SIZE),
            0
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        //Then
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        //Given
        with(fakeImageApiService) {
            val x = 10
            val breedList = mutableListOf<Image.Breed>().run {
                for (i in 0 until x)
                    add(
                        Image.Breed(
                            Image.Breed.Weight("$i", "$i"),
                            Image.Breed.Height("$i", "$i"),
                            "$i",
                            "$i",
                            "test",
                            "$i",
                            "9999",
                            "test",
                            "$i"
                        )
                    )

                toList()
            }
            val testImageList = mutableListOf<Image>().run {
                for (i in 0 until x)
                    add(Image(listOf(breedList[i]), i.toString(), "", i, i))

                toList()
            }

            imageList.addAll(testImageList)
            isThrowingError = false
        }

        //When
        val pagingState = PagingState<Int, ThumbnailEntity>(
            listOf(),
            null,
            PagingConfig(PAGE_SIZE),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        //Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        //Given

        //When
        val pagingState = PagingState<Int, ThumbnailEntity>(
            listOf(),
            null,
            PagingConfig(PAGE_SIZE),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        //Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result is RemoteMediator.MediatorResult.Success) && result.endOfPaginationReached)
    }
}