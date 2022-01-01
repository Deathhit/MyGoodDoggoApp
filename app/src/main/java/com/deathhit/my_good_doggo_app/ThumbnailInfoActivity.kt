package com.deathhit.my_good_doggo_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.thumbnail_info.ThumbnailInfoFragment
import com.deathhit.framework.toolbox.StateActivity

class ThumbnailInfoActivity :
    StateActivity<ThumbnailInfoActivityViewModel.State, ThumbnailInfoActivityViewModel>() {
    companion object {
        private const val TAG = "ThumbnailInfoActivity"
        private const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
        private const val TAG_THUMBNAIL_INFO = "$TAG.TAG_THUMBNAIL_INFO"
        private const val ID_CONTAINER_THUMBNAIL_INFO = R.id.activity_frameLayout_thumbnail_info
        private const val LAYOUT = R.layout.activity_thumbnail_info

        fun createIntent(context: Context, thumbnailVO: ThumbnailVO): Intent {
            val intent = Intent(context, ThumbnailInfoActivity::class.java)
            intent.putExtra(KEY_THUMBNAIL_VO, thumbnailVO)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        savedInstanceState ?: viewModel.addThumbnailInfoFragment()
    }

    override fun createViewModel(args: Bundle): ThumbnailInfoActivityViewModel {
        val viewModel: ThumbnailInfoActivityViewModel by viewModels()
        viewModel.thumbnailVO = args.getParcelable(KEY_THUMBNAIL_VO)
        return viewModel
    }

    override fun onFragmentAttach(fragment: Fragment) {

    }

    override fun onRenderState(state: ThumbnailInfoActivityViewModel.State) {
        state.eventAddThumbnailInfoFragment.signForEvent(this)
            ?.let { addThumbnailListFragment(it) }
    }

    override fun onSaveViewModelArgs(args: Bundle) {
        args.putParcelable(KEY_THUMBNAIL_VO, viewModel.thumbnailVO)
    }

    private fun addThumbnailListFragment(thumbnailVO: ThumbnailVO) {
        supportFragmentManager.beginTransaction().add(
            ID_CONTAINER_THUMBNAIL_INFO,
            ThumbnailInfoFragment.create(thumbnailVO),
            TAG_THUMBNAIL_INFO
        ).commit()
    }
}