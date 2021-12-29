package com.deathhit.my_good_doggo_app.thumbnail_list

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.domain.RepositoryProvider
import com.deathhit.domain.repository.ThumbnailRepository
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import com.deathhit.framework.StateViewModel
import com.deathhit.framework.Status
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ThumbnailListViewModel(application: Application) :
    StateViewModel<ThumbnailListViewModel.State>(application) {
    class State(
        val eventGoToThumbnailInfoActivity: Event<ThumbnailVO>,
        val statusThumbnailList: Status<PagingData<ThumbnailVO>>
    )

    private val thumbnailRepository: ThumbnailRepository =
        RepositoryProvider.getThumbnailRepository(getApplication())

    private val eventGoToThumbnailInfoActivity = StatePackage<ThumbnailVO>()
    private val statusThumbnailList = StatePackage<PagingData<ThumbnailVO>>()

    override fun createState(): State = State(eventGoToThumbnailInfoActivity, statusThumbnailList)

    fun goToThumbnailInfoActivity(thumbnailVO: ThumbnailVO) {
        eventGoToThumbnailInfoActivity.content = thumbnailVO
        postState()
    }

    fun loadThumbnailList() {
        viewModelScope.launch {
            thumbnailRepository.getThumbnailPager().flow
                .map { pagingData -> pagingData.map { ThumbnailVO.valueOf(it) } }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    statusThumbnailList.content = it
                    postState()
                }
        }
    }
}