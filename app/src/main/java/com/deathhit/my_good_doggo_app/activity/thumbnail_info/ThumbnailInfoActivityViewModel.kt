package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThumbnailInfoActivityViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    ViewModel() {
    companion object {
        private const val TAG = "ThumbnailInfoActivityViewModel"
        private const val KEY_THUMBNAIL = "$TAG.KEY_THUMBNAIL"

        fun createArgs(thumbnail: ThumbnailVO) = Bundle().apply {
            putParcelable(KEY_THUMBNAIL, thumbnail)
        }
    }

    sealed class Event {
        data class ShowImage(val imageUrl: String) : Event()
    }

    data class State(
        val thumbnail: ThumbnailVO
    )

    private val _eventChannel = Channel<Event>()
    val eventFlow = _eventChannel.receiveAsFlow()

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_THUMBNAIL]!!))
    val stateFlow = _stateFlow.asStateFlow()

    fun onThumbnailBannerClick(thumbnail: ThumbnailVO) {
        viewModelScope.launch {
            _eventChannel.send(Event.ShowImage(thumbnail.thumbnailUrl))
        }
    }
}