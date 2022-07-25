package com.deathhit.my_good_doggo_app.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    sealed class Event {
        data class GoToThumbnailInfo(val thumbnail: ThumbnailVO) : Event()
    }

    private val _eventChannel = Channel<Event>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onClickThumbnail(thumbnail: ThumbnailVO) {
        viewModelScope.launch {
            _eventChannel.send(Event.GoToThumbnailInfo(thumbnail))
        }
    }
}