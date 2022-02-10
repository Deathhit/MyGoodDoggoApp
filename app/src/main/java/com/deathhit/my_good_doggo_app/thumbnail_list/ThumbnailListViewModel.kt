package com.deathhit.my_good_doggo_app.thumbnail_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.domain.RepositoryProvider
import com.deathhit.domain.repository.ThumbnailRepository
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import com.deathhit.framework.Status
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ThumbnailListViewModel(application: Application) : AndroidViewModel(application) {
    data class State(
        val eventGoToThumbnailInfoActivity: Event<ThumbnailVO>,
        val statusThumbnailList: Status<PagingData<ThumbnailVO>>
    ) {
        constructor() : this(StatePackage(), StatePackage())
    }

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    private val thumbnailRepository: ThumbnailRepository =
        RepositoryProvider.getThumbnailRepository(getApplication())

    init {
        loadThumbnailList()
    }

    fun goToThumbnailInfoActivity(thumbnailVO: ThumbnailVO) {
        _stateFlow.update { it.copy(eventGoToThumbnailInfoActivity = StatePackage(thumbnailVO)) }
    }

    private fun loadThumbnailList() {
        viewModelScope.launch {
            thumbnailRepository.getThumbnailPager().flow
                .map { pagingData -> pagingData.map { ThumbnailVO.valueOf(it) } }
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _stateFlow.update { it.copy(statusThumbnailList = StatePackage(pagingData)) }
                }
        }
    }
}