package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.fragment.thumbnail_info.ThumbnailInfoFragment
import kotlinx.coroutines.flow.collect

class ThumbnailInfoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "com.deathhit.my_good_doggo_app.activity.thumbnail_info.ThumbnailInfoActivity"
        private const val TAG_THUMBNAIL_INFO = "$TAG.TAG_THUMBNAIL_INFO"

        private const val ID_CONTAINER_THUMBNAIL_INFO = R.id.activity_frameLayout_thumbnail_info
        private const val LAYOUT = R.layout.activity_thumbnail_info

        fun createIntent(context: Context, thumbnailVO: ThumbnailVO): Intent {
            val intent = Intent(context, ThumbnailInfoActivity::class.java)
            intent.putExtra(ThumbnailInfoActivityViewModel.KEY_THUMBNAIL_VO, thumbnailVO)
            return intent
        }
    }

    private val viewModel: ThumbnailInfoActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        savedInstanceState ?: viewModel.addThumbnailInfoFragment()

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                state.eventAddThumbnailInfoFragment.signForEvent(this@ThumbnailInfoActivity) {
                    addThumbnailListFragment(it)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    private fun addThumbnailListFragment(thumbnailVO: ThumbnailVO) {
        supportFragmentManager.beginTransaction().add(
            ID_CONTAINER_THUMBNAIL_INFO,
            ThumbnailInfoFragment.create(thumbnailVO),
            TAG_THUMBNAIL_INFO
        ).commit()
    }
}