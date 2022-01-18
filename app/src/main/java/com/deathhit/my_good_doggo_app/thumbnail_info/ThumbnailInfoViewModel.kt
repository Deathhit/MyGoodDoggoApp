package com.deathhit.my_good_doggo_app.thumbnail_info

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.deathhit.my_good_doggo_app.base.model.BreedVO
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.domain.RepositoryProvider
import com.deathhit.framework.StatePackage
import com.deathhit.framework.StateViewModel
import com.deathhit.framework.Status
import kotlinx.coroutines.launch

class ThumbnailInfoViewModel(application: Application, savedStateHandle: SavedStateHandle) :
    StateViewModel<ThumbnailInfoViewModel.State>(
        application
    ) {
    companion object {
        private const val TAG = "ThumbnailInfoViewModel"
        const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
    }

    class State(
        val statusBreedVOList: Status<List<BreedVO>>,
        val statusThumbnailVO: Status<ThumbnailVO>
    )

    private val breedRepository = RepositoryProvider.getBreedRepository(application)

    private val statusBreedVOList = StatePackage<List<BreedVO>>()
    private val statusThumbnailVO = StatePackage<ThumbnailVO>()

    private var thumbnailVO: ThumbnailVO? = savedStateHandle[KEY_THUMBNAIL_VO]

    init {
        loadBreedVOList()
        loadThumbnailVO()
    }

    override fun createState(): State = State(statusBreedVOList, statusThumbnailVO)

    private fun loadBreedVOList() {
        viewModelScope.launch {
            statusBreedVOList.content =
                breedRepository.getBreedListByThumbnailId(requireNotNull(thumbnailVO).thumbnailId)
                    .map { BreedVO.valueOf(it) }
            postState()
        }
    }

    private fun loadThumbnailVO() {
        statusThumbnailVO.content = thumbnailVO
        postState()
    }
}