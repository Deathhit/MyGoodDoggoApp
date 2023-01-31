package com.deathhit.feature.thumbnail

import androidx.lifecycle.SavedStateHandle
import com.deathhit.feature.thumbnail.activity.thumbnail_info.ThumbnailInfoActivityViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ThumbnailInfoActivityViewModelTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val thumbnailId = "0"

    private lateinit var viewModel: ThumbnailInfoActivityViewModel

    @Before
    fun before() {
        hiltRule.inject()

        viewModel = ThumbnailInfoActivityViewModel(
            SavedStateHandle.createHandle(
                null,
                ThumbnailInfoActivityViewModel.createArgs(thumbnailId)
            )
        )
    }

    @Test
    fun onActionShouldRemoveTheGivenAction() = runTest {
        //Given
        val url = "https://test.com/"

        viewModel.openImage(url)

        val action =
            viewModel.stateFlow.value.actions.last() as ThumbnailInfoActivityViewModel.State.Action.OpenImage

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
        assert(lastAction is ThumbnailInfoActivityViewModel.State.Action.OpenImage && lastAction.imageUrl == url)
    }

    @Test
    fun validateViewModelInitialState() = runTest {
        //Given

        //When

        //Then
        assert(viewModel.stateFlow.value.thumbnailId == thumbnailId)
    }
}