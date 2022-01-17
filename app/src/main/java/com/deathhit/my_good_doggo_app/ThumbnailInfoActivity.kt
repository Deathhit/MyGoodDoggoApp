package com.deathhit.my_good_doggo_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.thumbnail_info.ThumbnailInfoFragment
import com.deathhit.framework.toolbox.StateActivity

class ThumbnailInfoActivity :
    StateActivity<ThumbnailInfoActivityViewModel.State, ThumbnailInfoActivityViewModel>() {
    companion object {
        private const val TAG = "ThumbnailInfoActivity"
        private const val TAG_THUMBNAIL_INFO = "$TAG.TAG_THUMBNAIL_INFO"
        private const val ID_CONTAINER_THUMBNAIL_INFO = R.id.activity_frameLayout_thumbnail_info
        private const val LAYOUT = R.layout.activity_thumbnail_info

        fun createIntent(context: Context, thumbnailVO: ThumbnailVO): Intent {
            val intent = Intent(context, ThumbnailInfoActivity::class.java)
            intent.putExtra(ThumbnailInfoActivityViewModel.KEY_THUMBNAIL_VO, thumbnailVO)
            return intent
        }
    }

    override val viewModel: ThumbnailInfoActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        savedInstanceState ?: viewModel.addThumbnailInfoFragment()
    }

    override fun onRenderState(state: ThumbnailInfoActivityViewModel.State) {
        state.eventAddThumbnailInfoFragment.signForEvent(this)
            ?.let { addThumbnailListFragment(it) }
    }

    private fun addThumbnailListFragment(thumbnailVO: ThumbnailVO) {
        supportFragmentManager.beginTransaction().add(
            ID_CONTAINER_THUMBNAIL_INFO,
            ThumbnailInfoFragment.create(thumbnailVO),
            TAG_THUMBNAIL_INFO
        ).commit()
    }
}