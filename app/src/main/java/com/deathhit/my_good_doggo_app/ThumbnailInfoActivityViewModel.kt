package com.deathhit.my_good_doggo_app

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.framework.Event
import com.deathhit.framework.StatePackage
import com.deathhit.framework.StateViewModel

class ThumbnailInfoActivityViewModel(application: Application, savedStateHandle: SavedStateHandle) :
    StateViewModel<ThumbnailInfoActivityViewModel.State>(
        application, savedStateHandle
    ) {
    companion object {
        private const val TAG = "ThumbnailInfoActivityViewModel"
        const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
    }

    class State(val eventAddThumbnailInfoFragment: Event<ThumbnailVO>)

    private val eventAddThumbnailInfoFragment = StatePackage<ThumbnailVO>()

    private var thumbnailVO: ThumbnailVO? = savedStateHandle[KEY_THUMBNAIL_VO]

    override fun createState(): State = State(eventAddThumbnailInfoFragment)

    fun addThumbnailInfoFragment() {
        eventAddThumbnailInfoFragment.content = thumbnailVO
        postState()
    }
}