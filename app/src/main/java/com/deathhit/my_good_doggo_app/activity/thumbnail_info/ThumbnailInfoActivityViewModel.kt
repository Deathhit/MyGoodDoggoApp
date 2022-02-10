package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ThumbnailInfoActivityViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "ThumbnailInfoActivityViewModel"
        const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
    }

    data class State(val eventAddThumbnailInfoFragment: Event<ThumbnailVO>) {
        constructor() : this(StatePackage())
    }

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    private var thumbnailVO: ThumbnailVO? = savedStateHandle[KEY_THUMBNAIL_VO]

    fun addThumbnailInfoFragment() {
        _stateFlow.update {
            it.copy(eventAddThumbnailInfoFragment = StatePackage(thumbnailVO))
        }
    }

    fun saveState() {
        savedStateHandle[KEY_THUMBNAIL_VO] = thumbnailVO
    }
}