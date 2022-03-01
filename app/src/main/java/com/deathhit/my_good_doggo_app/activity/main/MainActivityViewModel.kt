package com.deathhit.my_good_doggo_app.activity.main

import androidx.lifecycle.ViewModel
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.lib_state_package.Event
import com.deathhit.lib_state_package.StatePackage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    data class State(
        val eventAddThumbnailListFragment: Event<Unit> = StatePackage(),
        val eventGoToThumbnailInfoActivity: Event<ThumbnailVO> = StatePackage(),
    )

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    fun addThumbnailListFragment() {
        _stateFlow.update { state -> state.copy(eventAddThumbnailListFragment = StatePackage(Unit)) }
    }

    fun goToThumbnailInfoActivity(thumbnailVO: ThumbnailVO) {
        _stateFlow.update { state ->
            state.copy(
                eventGoToThumbnailInfoActivity = StatePackage(
                    thumbnailVO
                )
            )
        }
    }
}