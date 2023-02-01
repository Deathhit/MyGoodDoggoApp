package com.deathhit.feature.thumbnail

import androidx.lifecycle.SavedStateHandle
import com.deathhit.data.breed.BreedDO
import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.feature.thumbnail.config.FakeGetBreedListFlowByThumbnailIdUseCase
import com.deathhit.feature.thumbnail.config.FakeGetThumbnailFlowByIdUseCase
import com.deathhit.feature.thumbnail.fragment.thumbnail_info.ThumbnailInfoViewModel
import com.deathhit.feature.thumbnail.model.toVO
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ThumbnailInfoViewModelTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val thumbnailId = "0"

    @Inject
    internal lateinit var fakeGetBreedListFlowByThumbnailIdUseCase: FakeGetBreedListFlowByThumbnailIdUseCase

    @Inject
    internal lateinit var fakeGetThumbnailFlowByIdUseCase: FakeGetThumbnailFlowByIdUseCase

    private lateinit var viewModel: ThumbnailInfoViewModel

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        hiltRule.inject()

        viewModel = ThumbnailInfoViewModel(
            fakeGetBreedListFlowByThumbnailIdUseCase,
            fakeGetThumbnailFlowByIdUseCase,
            SavedStateHandle.createHandle(null, ThumbnailInfoViewModel.createArgs(thumbnailId))
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun onActionShouldRemoveTheGivenAction() = runTest {
        //Given
        val url = "https://test.com/"
        viewModel.openImage(url)
        val action =
            viewModel.stateFlow.value.actions.last() as ThumbnailInfoViewModel.State.Action.OpenImage

        //When
        viewModel.onAction(action)

        //Then
        assert(!viewModel.stateFlow.value.actions.contains(action))
    }

    @Test
    fun openImageShouldAddAnActionWithGivenUrl() = runTest {
        //Given
        val url = "https://test.com/"

        //When
        viewModel.openImage(url)

        //Then
        val lastAction = viewModel.stateFlow.value.actions.last()
        assert(lastAction is ThumbnailInfoViewModel.State.Action.OpenImage && lastAction.imageUrl == url)
    }

    @Test
    fun validateViewModelInitialState() = runTest {
        //Given

        //When

        //Then
        assert(viewModel.stateFlow.value.thumbnailId == this@ThumbnailInfoViewModelTest.thumbnailId)
    }

    @Test
    fun viewModelShouldCollectBreedListAndThumbnail() = runTest {
        //Given
        var collectedState: ThumbnailInfoViewModel.State? = null
        val collectJob = launch {
            viewModel.stateFlow.collect {
                collectedState = it
            }
        }

        val fakeBreedList = listOf(
            BreedDO("0", "", "", "", "", ""),
            BreedDO("1", "", "", "", "", ""),
            BreedDO("2", "", "", "", "", "")
        )
        val fakeThumbnail = ThumbnailDO("0", "")

        //When
        fakeGetBreedListFlowByThumbnailIdUseCase.breedListFlow.update { fakeBreedList }
        fakeGetThumbnailFlowByIdUseCase.thumbnailFlow.update { fakeThumbnail }
        advanceUntilIdle()

        //Then
        collectedState?.run {
            assert(breedList == fakeBreedList.map { it.toVO() })
            assert(thumbnail == fakeThumbnail.toVO())
        } ?: assert(false)

        collectJob.cancel()
    }
}