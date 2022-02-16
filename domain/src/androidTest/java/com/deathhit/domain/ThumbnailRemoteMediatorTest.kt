package com.deathhit.domain

import androidx.paging.*
import com.deathhit.data_source_dog_api.response.Image
import com.deathhit.domain.model.ThumbnailDO
import com.deathhit.domain.repository.ThumbnailRepositoryImp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalPagingApi
@HiltAndroidTest
internal class ThumbnailRemoteMediatorTest {
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

    private val recordList = listOf(
        Image(listOf(breedList[0]), "0", "", 0, 0),
        Image(listOf(breedList[1]), "1", "", 0, 0),
        Image(listOf(breedList[2]), "2", "", 0, 0)
    )

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var apiService: TestImageApiService

    @Inject
    internal lateinit var domainDatabase: DomainDatabase

    private lateinit var remoteMediator: RemoteMediator<Int, ThumbnailDO>

    @Before
    fun before() {
        hiltRule.inject()
        apiService.list.addAll(recordList)

        remoteMediator =
            ThumbnailRepositoryImp(apiService, domainDatabase).createThumbnailRemoteMediator()
    }

    @After
    fun after() {
        apiService.list.clear()
        domainDatabase.clearAllTables()
    }

    @Test
    fun testLoadFirstPage() = runBlocking {
        val pagingState = PagingState<Int, ThumbnailDO>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun testLoadEmptyPage() = runBlocking {
        apiService.list.clear()
        val pagingState = PagingState<Int, ThumbnailDO>(
            listOf(),
            null,
            PagingConfig(3),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result is RemoteMediator.MediatorResult.Success) && result.endOfPaginationReached)
    }
}