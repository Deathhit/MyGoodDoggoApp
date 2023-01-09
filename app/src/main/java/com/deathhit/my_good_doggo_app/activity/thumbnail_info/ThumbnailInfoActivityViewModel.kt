package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ThumbnailInfoActivityViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    companion object {
        private const val TAG = "ThumbnailInfoActivityViewModel"
        private const val KEY_THUMBNAIL_ID = "$TAG.KEY_THUMBNAIL_ID"

        fun createArgs(thumbnailId: String) = Bundle().apply {
            putString(KEY_THUMBNAIL_ID, thumbnailId)
        }
    }

    data class State(
        val actions: List<Action>,
        val thumbnailId: String
    ) {
        sealed interface Action {
            data class OpenImage(val imageUrl: String) : Action
        }
    }

    private val _stateFlow = MutableStateFlow(
        State(
            actions = emptyList(),
            thumbnailId = savedStateHandle[KEY_THUMBNAIL_ID]!!
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun onAction(action: State.Action) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions - action)
        }
    }

    fun openImage(imageUrl: String) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions + State.Action.OpenImage(imageUrl))
        }
    }
}