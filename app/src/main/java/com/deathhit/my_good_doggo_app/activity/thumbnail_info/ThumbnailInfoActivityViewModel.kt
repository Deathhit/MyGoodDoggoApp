package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.deathhit.lib_sign_able.SignAble
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ThumbnailInfoActivityViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {
    companion object {
        private const val TAG = "ThumbnailInfoActivityViewModel"
        const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
    }

    data class State(
        val argThumbnailVO: ThumbnailVO,
        val eventAddThumbnailInfoFragment: SignAble<ThumbnailVO> = SignAble(),
        val eventShowImageViewerFragment: SignAble<String> = SignAble()
    )

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_THUMBNAIL_VO]!!))
    val stateFlow = _stateFlow.asStateFlow()

    fun addThumbnailInfoFragment() {
        _stateFlow.update { state ->
            state.copy(eventAddThumbnailInfoFragment = SignAble(state.argThumbnailVO))
        }
    }

    fun saveState() {
        stateFlow.value.run {
            savedStateHandle[KEY_THUMBNAIL_VO] = argThumbnailVO
        }
    }

    fun showImageViewerFragment(imageUrl: String) {
        _stateFlow.update { state -> state.copy(eventShowImageViewerFragment = SignAble(imageUrl)) }
    }
}