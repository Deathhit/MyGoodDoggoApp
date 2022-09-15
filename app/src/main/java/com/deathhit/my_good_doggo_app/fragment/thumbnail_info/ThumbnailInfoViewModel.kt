package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.domain.use_case.breed.GetBreedListByThumbnailIdUseCase
import com.deathhit.my_good_doggo_app.model.BreedVO
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.model.toVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThumbnailInfoViewModel @Inject constructor(
    private val getBreedListByThumbnailIdUseCase: GetBreedListByThumbnailIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TAG = "ThumbnailInfoViewModel"
        private const val KEY_THUMBNAIL = "$TAG.KEY_THUMBNAIL"

        fun createArgs(thumbnail: ThumbnailVO) = Bundle().apply {
            putParcelable(KEY_THUMBNAIL, thumbnail)
        }
    }

    sealed class Callback {
        data class OnBannerClick(val thumbnail: ThumbnailVO) : Callback()
    }

    data class State(val breedList: List<BreedVO>, val thumbnail: ThumbnailVO)

    private val _callbackChannel = Channel<Callback>()
    val callbackFlow = _callbackChannel.receiveAsFlow()

    private val _stateFlow =
        MutableStateFlow(State(emptyList(), savedStateHandle[KEY_THUMBNAIL]!!))
    val stateFlow = _stateFlow.asStateFlow()

    private val thumbnail get() = stateFlow.value.thumbnail

    init {
        viewModelScope.launch {
            val breedList =
                getBreedListByThumbnailIdUseCase(thumbnail.thumbnailId)
                    .map { it.toVO() }

            _stateFlow.update { state ->
                state.copy(breedList = breedList)
            }
        }
    }

    fun onBannerClick(thumbnailVO: ThumbnailVO) {
        viewModelScope.launch {
            _callbackChannel.send(Callback.OnBannerClick(thumbnailVO))
        }
    }
}