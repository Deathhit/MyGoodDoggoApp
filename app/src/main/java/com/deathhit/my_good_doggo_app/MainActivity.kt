package com.deathhit.my_good_doggo_app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.lifecycleScope
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.thumbnail_list.ThumbnailListFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "com.deathhit.my_good_doggo_app.MainActivity"
        private const val TAG_THUMBNAIL_LIST = "$TAG.TAG_THUMBNAIL_LIST"

        private const val ID_THUMBNAIL_LIST_CONTAINER = R.id.activity_thumbnailListContainer
        private const val LAYOUT = R.layout.activity_main
    }

    private val fragmentOnAttachListener: FragmentOnAttachListener =
        FragmentOnAttachListener { _, fragment ->
            onFragmentAttach(
                fragment
            )
        }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        savedInstanceState ?: viewModel.addThumbnailListFragment()

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                state.eventAddThumbnailListFragment.signForEvent(this@MainActivity) {
                    addThumbnailListFragment()
                }

                state.eventGoToThumbnailInfoActivity.signForEvent(this@MainActivity) {
                    goToThumbnailInfoActivity(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeFragmentOnAttachListener(fragmentOnAttachListener)
    }

    private fun onFragmentAttach(fragment: Fragment) {
        if (fragment is ThumbnailListFragment)
            lifecycleScope.launch {
                fragment.getStateFlow().collect { state ->
                    state.eventGoToThumbnailInfoActivity.signForEvent(this@MainActivity) {
                        viewModel.goToThumbnailInfoActivity(it)
                    }
                }
            }
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