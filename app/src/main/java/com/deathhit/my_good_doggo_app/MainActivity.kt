package com.deathhit.my_good_doggo_app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.thumbnail_list.ThumbnailListFragment
import com.deathhit.framework.toolbox.StateActivity
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : StateActivity<MainActivityViewModel.State, MainActivityViewModel>() {
    companion object {
        private const val TAG = "MainActivity"
        private const val TAG_THUMBNAIL_LIST = "$TAG.TAG_THUMBNAIL_LIST"

        private const val ID_THUMBNAIL_LIST_CONTAINER = R.id.activity_thumbnailListContainer
        private const val ID_TOOLBAR = R.id.activity_materialToolbar
        private const val LAYOUT = R.layout.activity_main
    }

    private var materialToolbar: MaterialToolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        materialToolbar = findViewById(ID_TOOLBAR)

        savedInstanceState ?: viewModel.addThumbnailListFragment()
    }

    override fun createViewModel(args: Bundle): MainActivityViewModel {
        val viewModel: MainActivityViewModel by viewModels()
        return viewModel
    }

    override fun onFragmentAttach(fragment: Fragment) {
        if (fragment is ThumbnailListFragment)
            observeState(fragment) { state ->
                state.eventGoToThumbnailInfoActivity.signForEvent(this)
                    ?.let { viewModel.goToThumbnailInfoActivity(it) }
            }
    }

    override fun onRenderState(state: MainActivityViewModel.State) {
        state.eventAddThumbnailListFragment.signForEvent(this)
            ?.let { addThumbnailListFragment() }
        state.eventGoToThumbnailInfoActivity.signForEvent(this)
            ?.let { goToThumbnailInfoActivity(it) }
    }

    override fun onSaveViewModelArgs(args: Bundle) {

    }

    private fun addThumbnailListFragment() {
        supportFragmentManager.beginTransaction().add(
            ID_THUMBNAIL_LIST_CONTAINER,
            ThumbnailListFragment.create(),
            TAG_THUMBNAIL_LIST
        ).commit()
    }

    private fun goToThumbnailInfoActivity(thumbnailVO: ThumbnailVO) {
        val intent = ThumbnailInfoActivity.createIntent(this, thumbnailVO)
        startActivity(intent)
    }
}