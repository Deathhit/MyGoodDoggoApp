package com.deathhit.feature.thumbnail

import com.deathhit.feature.thumbnail.activity.thumbnail_list.ThumbnailListActivityViewModel
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
class ThumbnailListActivityViewModelTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var viewModel: ThumbnailListActivityViewModel

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        hiltRule.inject()

        viewModel = ThumbnailListActivityViewModel()
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun goToThumbnailInfoShouldAddAnActionWithTheGivenId() = runTest {
        //Given
        val thumbnailId = "0"

        //When
        viewModel.goToThumbnailInfo(thumbnailId)

        //Then
        val lastAction = viewModel.stateFlow.value.actions.last()
        assert(lastAction is ThumbnailListActivityViewModel.State.Action.GoToThumbnailInfo && lastAction.thumbnailId == thumbnailId)
    }

    @Test
    fun onActionShouldRemoveTheGivenAction() = runTest {
        //Given
        viewModel.goToThumbnailInfo("0")

        val action = viewModel.stateFlow.value.actions.last()

        //When
        viewModel.onAction(action)

        //Then
        assert(viewModel.stateFlow.value.actions.lastOrNull() != action)
    }
}