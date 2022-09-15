package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.deathhit.domain.use_case.thumbnail.GetThumbnailListFlowUseCase
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.model.toVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThumbnailListViewModel @Inject constructor(getThumbnailListFlowUseCase: GetThumbnailListFlowUseCase) :
    ViewModel() {
    sealed class Callback {
        data class OnClickItem(val thumbnail: ThumbnailVO) : Callback()
    }

    private val _callbackChannel = Channel<Callback>()
    val callbackFlow = _callbackChannel.receiveAsFlow()

    val thumbnailListFlow =
        getThumbnailListFlowUseCase().map { pagingData -> pagingData.map { it.toVO() } }
            .cachedIn(viewModelScope)

    fun onClickItem(thumbnail: ThumbnailVO) {
        viewModelScope.launch {
            _callbackChannel.send(Callback.OnClickItem(thumbnail))
        }
    }
}