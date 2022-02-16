package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.domain.repository.breed.BreedRepository
import com.deathhit.my_good_doggo_app.model.BreedVO
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.framework.StatePackage
import com.deathhit.framework.Status
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
        val attrThumbnailVO: ThumbnailVO,
        val statusBreedVOList: Status<List<BreedVO>>,
        val statusThumbnailVOUpdated: Status<Unit>
    )

    private val _stateFlow = MutableStateFlow(
        State(
            savedStateHandle[KEY_THUMBNAIL_VO]!!,
            StatePackage(),
            StatePackage()
        )
    )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        bindThumbnailVO()
        loadBreedVOList()
    }

    fun saveState() {
        savedStateHandle[KEY_THUMBNAIL_VO] = stateFlow.value.attrThumbnailVO
    }

    private fun bindThumbnailVO() {
        _stateFlow.update { state ->
            state.copy(statusThumbnailVOUpdated = StatePackage(Unit))
        }
    }

    private fun loadBreedVOList() {
        viewModelScope.launch {
            _stateFlow.update { state ->
                state.copy(
                    statusBreedVOList = StatePackage(breedRepository.getBreedListByThumbnailId(
                        state.attrThumbnailVO.thumbnailId
                    ).map { BreedVO.valueOf(it) })
                )
            }
        }
    }
}