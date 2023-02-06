package com.deathhit.feature.thumbnail

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.deathhit.data.thumbnail.ThumbnailDO
import com.deathhit.feature.thumbnail.config.FakeGetThumbnailPagingDataFlowUseCase
import com.deathhit.feature.thumbnail.fragment.thumbnail_list.ThumbnailListViewModel
import com.deathhit.feature.thumbnail.model.Thumbnail
import com.deathhit.feature.thumbnail.model.toThumbnail
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
class ThumbnailListViewModelTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var fakeGetThumbnailPagingDataFlowUseCase: FakeGetThumbnailPagingDataFlowUseCase

    private lateinit var viewModel: ThumbnailListViewModel

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())

        hiltRule.inject()

        viewModel = ThumbnailListViewModel(fakeGetThumbnailPagingDataFlowUseCase)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun openThumbnailShouldAddAnActionWithTheGivenId() = runTest {
        //Given
        val thumbnail = Thumbnail("0", "")

        //When
        viewModel.openThumbnail(thumbnail)

        //Then
        val lastAction = viewModel.stateFlow.value.actions.last()
        assert(lastAction is ThumbnailListViewModel.State.Action.OpenThumbnail && lastAction.thumbnailId == thumbnail.thumbnailId)
    }

    @Test
    fun onActionShouldRemoveTheGivenAction() = runTest {
        //Given
        viewModel.openThumbnail(Thumbnail("0", ""))
        val action = viewModel.stateFlow.value.actions.last()

        //When
        viewModel.onAction(action)

        //Then
        assert(!viewModel.stateFlow.value.actions.contains(action))
    }

    @Test
    fun viewModelShouldCollectThumbnailPagingData() = runTest {
        //Given
        var collectedThumbnailPagingData: PagingData<Thumbnail>? = null
        val collectJob = launch {
            viewModel.thumbnailPagingDataFlow.collect {
                collectedThumbnailPagingData = it
            }
        }

        val differ = AsyncPagingDataDiffer(
            object : DiffUtil.ItemCallback<Thumbnail>() {
                override fun areItemsTheSame(oldItem: Thumbnail, newItem: Thumbnail): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: Thumbnail, newItem: Thumbnail): Boolean =
                    oldItem == newItem
            }, object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {}

                override fun onRemoved(position: Int, count: Int) {}

                override fun onMoved(fromPosition: Int, toPosition: Int) {}

                override fun onChanged(position: Int, count: Int, payload: Any?) {}
            }, Dispatchers.Main
        )

        val fakeThumbnailList = listOf(ThumbnailDO("0", ""))
        val fakeThumbnailPagingData = PagingData.from(fakeThumbnailList)

        //When
        fakeGetThumbnailPagingDataFlowUseCase.thumbnailPagingDataFlow.update { fakeThumbnailPagingData }
        advanceUntilIdle()

        differ.submitData(collectedThumbnailPagingData ?: PagingData.empty())
        advanceUntilIdle()

        //Then
        assert(differ.snapshot().items == fakeThumbnailList.map { it.toThumbnail() })

        collectJob.cancel()
    }
}