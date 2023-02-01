package com.deathhit.feature.image_viewer

import androidx.lifecycle.SavedStateHandle
import com.deathhit.feature.image_viewer.fragment.ImageViewerViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class ImageViewerViewModelTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val imageUrl = "https://test.com/"

    private lateinit var viewModel: ImageViewerViewModel

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        hiltRule.inject()

        viewModel = ImageViewerViewModel(
            SavedStateHandle.createHandle(
                null,
                ImageViewerViewModel.createArgs(imageUrl)
            )
        )
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun closeShouldAddAnAction() = runTest {
        //Given

        //When
        viewModel.close()

        //Then
        val lastAction = viewModel.stateFlow.value.actions.last()
        assert(lastAction is ImageViewerViewModel.State.Action.Close)
    }

    @Test
    fun onActionShouldRemoveTheGivenAction() = runTest {
        //Given
        viewModel.close()
        val action = viewModel.stateFlow.value.actions.last()

        //When
        viewModel.onAction(action)

        //Then
        assert(!viewModel.stateFlow.value.actions.contains(action))
    }

    @Test
    fun validateViewModelInitialState() = runTest {
        //Given

        //When

        //Then
        assert(viewModel.stateFlow.value.imageUrl == this@ImageViewerViewModelTest.imageUrl)
    }
}