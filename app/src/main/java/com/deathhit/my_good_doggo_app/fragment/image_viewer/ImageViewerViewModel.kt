package com.deathhit.my_good_doggo_app.fragment.image_viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.deathhit.lib_sign_able.SignAble
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ImageViewerViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {
    companion object {
        private const val TAG =
            "com.deathhit.my_good_doggo_app.fragment.image_viewer.ImageViewerViewModel"
        const val KEY_IMAGE_URL = "$TAG.KEY_IMAGE_URL"
    }

    data class State(
        val argImageUrl: String,
        val statusImageUrl: SignAble<String> = SignAble()
    )

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_IMAGE_URL]!!))
    val stateFlow = _stateFlow.asStateFlow()

    init {
        bindStatusImageUrl()
    }

    fun saveState() {
        savedStateHandle[KEY_IMAGE_URL] = stateFlow.value.argImageUrl
    }

    private fun bindStatusImageUrl() {
        _stateFlow.update { state -> state.copy(statusImageUrl = SignAble(state.argImageUrl)) }
    }
}