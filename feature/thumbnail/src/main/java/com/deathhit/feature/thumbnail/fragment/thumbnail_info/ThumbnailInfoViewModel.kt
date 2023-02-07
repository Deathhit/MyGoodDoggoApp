package com.deathhit.feature.thumbnail.fragment.thumbnail_info

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deathhit.feature.thumbnail.model.BreedVO
import com.deathhit.feature.thumbnail.model.ThumbnailVO
import com.deathhit.feature.thumbnail.model.toBreedVO
import com.deathhit.feature.thumbnail.model.toThumbnailVO
import com.deathhit.use_case.breed.GetBreedListFlowByThumbnailIdUseCase
import com.deathhit.use_case.thumbnail.GetThumbnailFlowByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class ThumbnailInfoViewModel @Inject constructor(
    private val getBreedListFlowByThumbnailIdUseCase: GetBreedListFlowByThumbnailIdUseCase,
    private val getThumbnailFlowByIdUseCase: GetThumbnailFlowByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val TAG = "ThumbnailInfoViewModel"
        private const val KEY_THUMBNAIL_ID = "$TAG.KEY_THUMBNAIL_ID"

        fun createArgs(thumbnailId: String) = Bundle().apply {
            putString(KEY_THUMBNAIL_ID, thumbnailId)
        }
    }

    data class State(
        val actions: List<Action>,
        val breedList: List<BreedVO>,
        val thumbnail: ThumbnailVO?,
        val thumbnailId: String
    ) {
        sealed interface Action {
            data class OpenImage(val imageUrl: String) : Action
        }
    }

    private val _stateFlow =
        MutableStateFlow(
            State(
                actions = emptyList(),
                breedList = emptyList(),
                thumbnail = null,
                thumbnailId = savedStateHandle[KEY_THUMBNAIL_ID]!!
            )
        )
    val stateFlow = _stateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                stateFlow.map { it.thumbnailId }.flatMapLatest { thumbnailId ->
                    getBreedListFlowByThumbnailIdUseCase(thumbnailId).map { breedDOList -> breedDOList.map { it.toBreedVO() } }
                }.distinctUntilChanged().collect {
                    _stateFlow.update { state ->
                        state.copy(breedList = it)
                    }
                }
            }

            launch {
                stateFlow.map { it.thumbnailId }.flatMapLatest { thumbnailId ->
                    getThumbnailFlowByIdUseCase(thumbnailId).map { it?.toThumbnailVO() }
                }.distinctUntilChanged().collect {
                    _stateFlow.update { state ->
                        state.copy(thumbnail = it)
                    }
                }
            }
        }
    }

    fun onAction(action: State.Action) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions - action)
        }
    }

    fun openImage(imageUrl: String) {
        _stateFlow.update { state ->
            state.copy(actions = state.actions + State.Action.OpenImage(imageUrl))
        }
    }
}