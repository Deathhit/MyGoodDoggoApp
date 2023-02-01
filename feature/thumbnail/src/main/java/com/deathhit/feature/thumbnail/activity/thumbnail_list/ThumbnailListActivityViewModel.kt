package com.deathhit.feature.thumbnail.activity.thumbnail_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ThumbnailListActivityViewModel @Inject constructor() : ViewModel() {
    data class State(val actions: List<Action>) {
        sealed interface Action {
            data class GoToThumbnailInfo(val thumbnailId: String) : Action
        }
    }

    private val _stateFlow = MutableStateFlow(State(actions = emptyList()))
    val stateFlow = _stateFlow.asStateFlow()

    fun goToThumbnailInfo(thumbnailId: String) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions + State.Action.GoToThumbnailInfo(thumbnailId))
        }
    }

    fun onAction(action: State.Action) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions - action)
        }
    }
}