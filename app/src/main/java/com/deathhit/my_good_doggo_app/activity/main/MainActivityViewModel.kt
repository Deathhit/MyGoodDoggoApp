package com.deathhit.my_good_doggo_app.activity.main

import androidx.lifecycle.ViewModel
import com.deathhit.lib_sign_able.SignAble
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {
    data class State(val eventGoToThumbnailInfoActivity: SignAble<ThumbnailVO> = SignAble())

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    fun goToThumbnailInfoActivity(thumbnailVO: ThumbnailVO) {
        _stateFlow.update { state ->
            state.copy(eventGoToThumbnailInfoActivity = SignAble(thumbnailVO))
        }
    }
}