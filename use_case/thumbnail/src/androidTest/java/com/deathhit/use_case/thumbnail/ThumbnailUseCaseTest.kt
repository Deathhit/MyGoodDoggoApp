package com.deathhit.use_case.thumbnail

import androidx.paging.PagingData
import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.use_case.thumbnail.config.FakeThumbnailRepository
import com.deathhit.use_case.thumbnail.use_case.GetThumbnailFlowByIdUseCase
import com.deathhit.use_case.thumbnail.use_case.GetThumbnailListFlowUseCase
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
class ThumbnailUseCaseTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var fakeThumbnailRepository: FakeThumbnailRepository

    @Inject
    internal lateinit var getThumbnailFlowByIdUseCase: GetThumbnailFlowByIdUseCase

    @Inject
    internal lateinit var getThumbnailListFlowUseCase: GetThumbnailListFlowUseCase

    @Before
    fun before() {
        hiltRule.inject()

        with(fakeThumbnailRepository) {
            thumbnailFlow.update { null }
            thumbnailPagingDataFlow.update { PagingData.empty() }
        }
    }

    @Test
    fun getThumbnailFlowByIdUseCaseReturnsTheCorrespondObjInRepository() = runBlocking {
        //Given
        val fakeThumbnail = ThumbnailDO("0", "")
        fakeThumbnailRepository.thumbnailFlow.update { fakeThumbnail }

        //When
        val result = getThumbnailFlowByIdUseCase("0").first()

        //Then
        assert(fakeThumbnail.thumbnailId == result?.thumbnailId)
    }

    @Test
    fun getThumbnailListFlowUseCaseReturnsTheCorrespondPagingDataInRepository() = runBlocking {
        //Given
        val fakePagingData = PagingData.empty<ThumbnailDO>()
        fakeThumbnailRepository.thumbnailPagingDataFlow.update { fakePagingData }

        //When
        val result = getThumbnailListFlowUseCase().first()

        //Then
        assert(fakePagingData == result)
    }
}