package com.deathhit.feature.thumbnail.fragment.thumbnail_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.deathhit.feature.thumbnail.model.ThumbnailVO
import com.deathhit.feature.thumbnail.model.toVO
import com.deathhit.use_case.thumbnail.GetThumbnailPagingDataFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ThumbnailListViewModel @Inject constructor(getThumbnailPagingDataFlowUseCase: GetThumbnailPagingDataFlowUseCase) :
    ViewModel() {
    data class State(val actions: List<Action>) {
        sealed interface Action {
            data class OpenThumbnail(val thumbnailId: String) : Action
        }
    }

    private val _stateFlow = MutableStateFlow(State(actions = emptyList()))
    val stateFlow = _stateFlow.asStateFlow()

    val thumbnailPagingDataFlow =
        getThumbnailPagingDataFlowUseCase().map { pagingData -> pagingData.map { it.toVO() } }
            .cachedIn(viewModelScope)

    fun onAction(action: State.Action) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions - action)
        }
    }

    fun openThumbnail(thumbnail: ThumbnailVO) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions + State.Action.OpenThumbnail(thumbnail.thumbnailId))
        }
    }
}