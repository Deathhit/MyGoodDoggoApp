package com.deathhit.domain

import androidx.paging.*
import com.deathhit.data_source_dog_api.ImageApiService
import com.deathhit.data_source_dog_api.response.Image
import com.deathhit.domain.model.ThumbnailDO
import com.deathhit.domain.repository.thumbnail.ThumbnailRemoteMediator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@ExperimentalPagingApi
@HiltAndroidTest
internal class ThumbnailRemoteMediatorTest {
    companion object {
        private const val PAGE_SIZE = 25
    }

    private class TestImageApiService : ImageApiService {
        val mutableImageList = mutableListOf<Image>()

        override suspend fun searchImage(
            size: String?,
            hasBreeds: Boolean?,
            order: String?,
            page: Int?,
            limit: Int?
        ): List<Image> = mutableImageList
    }

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

    private val testImageApiService = TestImageApiService()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var domainDatabase: DomainDatabase

    private lateinit var remoteMediator: ThumbnailRemoteMediator

    @Before
    fun before() {
        hiltRule.inject()

        remoteMediator = ThumbnailRemoteMediator(domainDatabase, testImageApiService)
    }

    @After
    fun after() {
        domainDatabase.clearAllTables()
    }

    @Test
    fun testLoadFirstPage() = runBlocking {
        testImageApiService.mutableImageList.addAll(imageList)
        val pagingState = PagingState<Int, ThumbnailDO>(
            listOf(),
            null,
            PagingConfig(PAGE_SIZE),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun testLoadEmptyPage() = runBlocking {
        testImageApiService.mutableImageList.clear()
        val pagingState = PagingState<Int, ThumbnailDO>(
            listOf(),
            null,
            PagingConfig(PAGE_SIZE),
            0
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result is RemoteMediator.MediatorResult.Success) && result.endOfPaginationReached)
    }
}