package com.deathhit.my_good_doggo_app

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import com.deathhit.framework.StateViewModel

class MainActivityViewModel(application: Application, savedStateHandle: SavedStateHandle) :
    StateViewModel<MainActivityViewModel.State>(application, savedStateHandle) {
    class State(
        val eventAddThumbnailListFragment: Event<Unit>,
        val eventGoToThumbnailInfoActivity: Event<ThumbnailVO>,
    )

    private val eventAddThumbnailListFragment = StatePackage<Unit>()
    private val eventGoToThumbnailInfoActivity = StatePackage<ThumbnailVO>()

    override fun createState(): State = State(
        eventAddThumbnailListFragment,
        eventGoToThumbnailInfoActivity
    )

    fun addThumbnailListFragment() {
        eventAddThumbnailListFragment.content = Unit
        postState()
    }

    fun goToThumbnailInfoActivity(thumbnailVO: ThumbnailVO) {
        eventGoToThumbnailInfoActivity.content = thumbnailVO
        postState()
    }
}