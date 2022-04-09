package com.deathhit.my_good_doggo_app.fragment.image_viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ImageViewerViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {
    companion object {
        private const val TAG = "ImageViewerViewModel"
        const val KEY_IMAGE_URL = "$TAG.KEY_IMAGE_URL"
    }

    data class State(val argImageUrl: String)

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_IMAGE_URL]!!))
    val stateFlow = _stateFlow.asStateFlow()

    fun saveState() {
        savedStateHandle[KEY_IMAGE_URL] = stateFlow.value.argImageUrl
    }
}