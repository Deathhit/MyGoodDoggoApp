package com.deathhit.feature.image_viewer

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ImageViewerViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    companion object {
        private const val TAG = "ImageViewerViewModel"
        private const val KEY_IMAGE_URL = "$TAG.KEY_IMAGE_URL"

        fun createArgs(imageUrl: String) = Bundle().apply {
            putString(KEY_IMAGE_URL, imageUrl)
        }
    }

    data class State(val actions: List<Action>, val imageUrl: String) {
        sealed interface Action {
            object Close : Action
        }
    }

    private val _stateFlow =
        MutableStateFlow(State(actions = emptyList(), imageUrl = savedStateHandle[KEY_IMAGE_URL]!!))
    val stateFlow = _stateFlow.asStateFlow()

    fun close() {
        _stateFlow.update { state ->
            state.copy(actions = state.actions + State.Action.Close)
        }
    }

    fun onAction(action: State.Action) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions - action)
        }
    }
}