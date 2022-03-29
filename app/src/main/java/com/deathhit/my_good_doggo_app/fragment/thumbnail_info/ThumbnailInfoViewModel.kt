package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.domain.repository.breed.BreedRepository
import com.deathhit.lib_sign_able.SignAble
import com.deathhit.my_good_doggo_app.model.BreedVO
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThumbnailInfoViewModel @Inject constructor(
    private val breedRepository: BreedRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    companion object {
        private const val TAG = "ThumbnailInfoViewModel"
        const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
    }

    data class State(
        val argThumbnailVO: ThumbnailVO,
        val eventShowImageViewerFragment: SignAble<String> = SignAble(),
        val statusBreedVOList: SignAble<List<BreedVO>> = SignAble(),
        val statusThumbnailVO: SignAble<ThumbnailVO> = SignAble()
    )

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_THUMBNAIL_VO]!!))
    val stateFlow = _stateFlow.asStateFlow()

    init {
        _stateFlow.update { state ->
            state.copy(statusThumbnailVO = SignAble(state.argThumbnailVO))
        }

        loadBreedVOList()
    }

    fun saveState() {
        stateFlow.value.run {
            savedStateHandle[KEY_THUMBNAIL_VO] = argThumbnailVO
        }
    }

    fun viewImage(thumbnailVO: ThumbnailVO?) {
        _stateFlow.update { state ->
            state.copy(eventShowImageViewerFragment = SignAble(thumbnailVO?.thumbnailUrl))
        }
    }

    private fun loadBreedVOList() {
        viewModelScope.launch {
            _stateFlow.update { state ->
                state.copy(
                    statusBreedVOList = SignAble(
                        breedRepository.getBreedListByThumbnailId(
                            state.argThumbnailVO.thumbnailId
                        ).map { BreedVO.valueOf(it) }
                    )
                )
            }
        }
    }
}