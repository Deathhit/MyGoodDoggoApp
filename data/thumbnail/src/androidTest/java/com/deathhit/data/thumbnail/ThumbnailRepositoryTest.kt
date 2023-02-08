package com.deathhit.data.thumbnail

import com.deathhit.core.database.AppDatabase
import com.deathhit.core.database.model.ThumbnailEntity
import com.deathhit.data.thumbnail.repository.ThumbnailRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ThumbnailRepositoryTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var appDatabase: AppDatabase

    @Inject
    internal lateinit var thumbnailRepository: ThumbnailRepository

    @Before
    fun before() {
        hiltRule.inject()
    }

    @After
    fun after() {
        appDatabase.close()
    }

    @Test
    fun getThumbnailFlowByIdFirstReturnsTheCorrespondObj() = runTest {
        //Given
        val entity = ThumbnailEntity("12345", "thumbnailUrl")
        appDatabase.thumbnailDao().upsert(listOf(entity))

        //When
        val domainObj = thumbnailRepository.getThumbnailFlowById(entity.thumbnailId).first()

        //Then
        assert(entity.thumbnailId == domainObj?.thumbnailId)
        assert(entity.thumbnailUrl == domainObj?.thumbnailUrl)
    }
}