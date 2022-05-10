package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.domain.repository.breed.BreedRepository
import com.deathhit.lib_sign_able.SignAble
import com.deathhit.my_good_doggo_app.extensions.toVO
import com.deathhit.my_good_doggo_app.model.BreedVO
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        val statusBreedVOList: SignAble<List<BreedVO>> = SignAble()
    )

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_THUMBNAIL_VO]!!))
    val stateFlow = _stateFlow.asStateFlow()

    init {
        with(stateFlow.value) {
            viewModelScope.launch(Dispatchers.IO) {
                val breedList =
                    breedRepository.getBreedListByThumbnailId(argThumbnailVO.thumbnailId)
                        .map { it.toVO() }

                launch(Dispatchers.Main) {
                    _stateFlow.update { state ->
                        state.copy(
                            statusBreedVOList = SignAble(breedList)
                        )
                    }
                }
            }
        }
    }

    fun saveState() {
        savedStateHandle[KEY_THUMBNAIL_VO] = stateFlow.value.argThumbnailVO
    }

    fun viewImage(thumbnailVO: ThumbnailVO?) {
        _stateFlow.update { state ->
            state.copy(eventShowImageViewerFragment = SignAble(thumbnailVO?.thumbnailUrl))
        }
    }
}