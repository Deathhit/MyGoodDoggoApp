package com.deathhit.my_good_doggo_app

import android.app.Application
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import com.deathhit.framework.StateViewModel

class ThumbnailInfoActivityViewModel(application: Application) :
    StateViewModel<ThumbnailInfoActivityViewModel.State>(
        application
    ) {
    class State(val eventAddThumbnailInfoFragment: Event<ThumbnailVO>)

    private val eventAddThumbnailInfoFragment = StatePackage<ThumbnailVO>()

    var thumbnailVO: ThumbnailVO? = null

    override fun createState(): State {
        return State(eventAddThumbnailInfoFragment)
    }

    fun addThumbnailInfoFragment() {
        eventAddThumbnailInfoFragment.content = thumbnailVO
        postState()
    }
}