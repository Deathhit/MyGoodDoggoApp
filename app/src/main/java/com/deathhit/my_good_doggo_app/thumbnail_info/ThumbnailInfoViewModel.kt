package com.deathhit.my_good_doggo_app.thumbnail_info

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.deathhit.my_good_doggo_app.base.model.BreedVO
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.domain.RepositoryProvider
import com.deathhit.framework.StatePackage
import com.deathhit.framework.StateViewModel
import com.deathhit.framework.Status
import kotlinx.coroutines.launch

class ThumbnailInfoViewModel(application: Application) :
    StateViewModel<ThumbnailInfoViewModel.State>(
        application
    ) {
    class State(
        val statusBreedList: Status<List<BreedVO>>
    )

    private val breedRepository = RepositoryProvider.getBreedRepository(application)

    private val statusBreedList = StatePackage<List<BreedVO>>()

    var thumbnailVO: ThumbnailVO? = null

    override fun createState(): State = State(statusBreedList)

    fun loadBreedList() {
        viewModelScope.launch {
            statusBreedList.content =
                breedRepository.getBreedListByThumbnailId(requireNotNull(thumbnailVO).thumbnailId)
                    .map { BreedVO.valueOf(it) }
            postState()
        }
    }
}