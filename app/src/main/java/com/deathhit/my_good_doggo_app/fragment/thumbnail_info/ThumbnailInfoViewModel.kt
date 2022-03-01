package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.domain.repository.breed.BreedRepository
import com.deathhit.lib_state_package.Event
import com.deathhit.my_good_doggo_app.model.BreedVO
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.lib_state_package.StatePackage
import com.deathhit.lib_state_package.Status
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
        private const val TAG =
            "com.deathhit.my_good_doggo_app.fragment.thumbnail_info.ThumbnailInfoViewModel"
        const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
    }

    data class State(
        val argThumbnailVO: ThumbnailVO,
        val eventShowImageViewerFragment: Event<String> = StatePackage(),
        val statusBreedVOList: Status<List<BreedVO>> = StatePackage(),
        val statusThumbnailVO: Status<ThumbnailVO> = StatePackage()
    )

    private val _stateFlow = MutableStateFlow(State(savedStateHandle[KEY_THUMBNAIL_VO]!!))
    val stateFlow = _stateFlow.asStateFlow()

    init {
        bindAttrStatus()
        loadBreedVOList()
    }

    fun saveState() {
        savedStateHandle[KEY_THUMBNAIL_VO] = stateFlow.value.argThumbnailVO
    }

    fun viewImage(thumbnailVO: ThumbnailVO?) {
        _stateFlow.update { state ->
            state.copy(eventShowImageViewerFragment = StatePackage(thumbnailVO?.thumbnailUrl))
        }
    }

    private fun bindAttrStatus() {
        _stateFlow.update { state ->
            state.copy(statusThumbnailVO = StatePackage(state.argThumbnailVO))
        }
    }

    private fun loadBreedVOList() {
        viewModelScope.launch {
            _stateFlow.update { state ->
                state.copy(
                    statusBreedVOList = StatePackage(breedRepository.getBreedListByThumbnailId(
                        state.argThumbnailVO.thumbnailId
                    ).map { BreedVO.valueOf(it) })
                )
            }
        }
    }
}