package com.deathhit.my_good_doggo_app

import android.app.Application
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import com.deathhit.framework.StateViewModel

class MainActivityViewModel(application: Application) :
    StateViewModel<MainActivityViewModel.State>(application) {
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