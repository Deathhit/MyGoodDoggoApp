package com.deathhit.my_good_doggo_app.thumbnail_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.deathhit.my_good_doggo_app.base.model.BreedVO
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.domain.RepositoryProvider
import com.deathhit.framework.StatePackage
import com.deathhit.framework.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThumbnailInfoViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) :
    AndroidViewModel(
        application
    ) {
    companion object {
        private const val TAG = "com.deathhit.my_good_doggo_app.thumbnail_info.ThumbnailInfoViewModel"
        const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
    }

    data class State(
        val statusBreedVOList: Status<List<BreedVO>>,
        val statusThumbnailVO: Status<ThumbnailVO>
    ) {
        constructor() : this(StatePackage(), StatePackage())
    }

    private val _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    private val breedRepository = RepositoryProvider.getBreedRepository(application)

    private var thumbnailVO: ThumbnailVO? = savedStateHandle[KEY_THUMBNAIL_VO]

    init {
        loadBreedVOList()
        loadThumbnailVO()
    }

    fun saveState() {
        savedStateHandle[KEY_THUMBNAIL_VO] = thumbnailVO
    }

    private fun loadBreedVOList() {
        viewModelScope.launch {
            _stateFlow.update { state ->
                state.copy(
                    statusBreedVOList = StatePackage(breedRepository.getBreedListByThumbnailId(
                        requireNotNull(thumbnailVO).thumbnailId
                    ).map { BreedVO.valueOf(it) })
                )
            }
        }
    }

    private fun loadThumbnailVO() {
        _stateFlow.update { state ->
            state.copy(statusThumbnailVO = StatePackage(thumbnailVO))
        }
    }
}