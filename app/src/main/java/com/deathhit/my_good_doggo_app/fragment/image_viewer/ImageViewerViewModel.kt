package com.deathhit.my_good_doggo_app.fragment.image_viewer

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageViewerViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    companion object {
        private const val TAG = "ImageViewerViewModel"
        private const val KEY_IMAGE_URL = "$TAG.KEY_IMAGE_URL"

        fun createArgs(imageUrl: String) = Bundle().apply {
            putString(KEY_IMAGE_URL, imageUrl)
        }
    }

    sealed class Event {
        object Close : Event()
    }

    data class State(val imageUrl: String)

    private val _eventChannel = Channel<Event>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_IMAGE_URL]!!))
    val stateFlow = _stateFlow.asStateFlow()

    fun onClose() {
        viewModelScope.launch {
            _eventChannel.send(Event.Close)
        }
    }
}