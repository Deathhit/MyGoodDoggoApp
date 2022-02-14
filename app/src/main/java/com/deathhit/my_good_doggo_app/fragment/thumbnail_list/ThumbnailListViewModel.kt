package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.domain.repository.ThumbnailRepository
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import com.deathhit.framework.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThumbnailListViewModel @Inject constructor(private val thumbnailRepository: ThumbnailRepository) :
    ViewModel() {
    data class State(
        val eventGoToThumbnailInfoActivity: Event<ThumbnailVO>,
        val statusThumbnailList: Status<PagingData<ThumbnailVO>>
    ) {
        constructor() : this(StatePackage(), StatePackage())
    }

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

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