package com.deathhit.my_good_doggo_app.activity.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityViewModel(application: Application) :
    AndroidViewModel(application) {
    data class State(
        val eventAddThumbnailListFragment: Event<Unit>,
        val eventGoToThumbnailInfoActivity: Event<ThumbnailVO>,
    ) {
        constructor() : this(StatePackage(), StatePackage())
    }

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    fun addThumbnailListFragment() {
        _stateFlow.update { it.copy(eventAddThumbnailListFragment = StatePackage(Unit)) }
    }

    fun goToThumbnailInfoActivity(thumbnailVO: ThumbnailVO) {
        _stateFlow.update { it.copy(eventGoToThumbnailInfoActivity = StatePackage(thumbnailVO)) }
    }
}