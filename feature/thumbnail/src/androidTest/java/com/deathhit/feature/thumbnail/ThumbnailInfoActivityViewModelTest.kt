package com.deathhit.feature.thumbnail

import androidx.lifecycle.SavedStateHandle
import com.deathhit.feature.thumbnail.activity.thumbnail_info.ThumbnailInfoActivityViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
        Dispatchers.setMain(StandardTestDispatcher())

        hiltRule.inject()

        viewModel = ThumbnailInfoActivityViewModel(
            SavedStateHandle.createHandle(
                null,
                ThumbnailInfoActivityViewModel.createArgs(thumbnailId)
            )
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun onActionShouldRemoveTheGivenAction() = runTest {
        //Given
        viewModel.openImage("https://test.com/")

        val action = viewModel.stateFlow.value.actions.last()

        //When
        viewModel.onAction(action)

        //Then
        assert(viewModel.stateFlow.value.actions.lastOrNull() != action)
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