package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.my_good_doggo_app.databinding.ActivityThumbnailInfoBinding
import com.deathhit.my_good_doggo_app.fragment.image_viewer.ImageViewerFragment
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.fragment.thumbnail_info.ThumbnailInfoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailInfoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ThumbnailInfoActivity"
        private const val TAG_IMAGE_VIEWER = "$TAG.TAG_IMAGE_VIEWER"
        private const val TAG_THUMBNAIL_INFO = "$TAG.TAG_THUMBNAIL_INFO"

        fun createIntent(context: Context, thumbnailVO: ThumbnailVO) =
            Intent(context, ThumbnailInfoActivity::class.java).apply {
                putExtra(ThumbnailInfoActivityViewModel.KEY_THUMBNAIL_VO, thumbnailVO)
            }
    }

    private lateinit var binding: ActivityThumbnailInfoBinding

    private val viewModel: ThumbnailInfoActivityViewModel by viewModels()

    private val fragmentOnAttachListener: FragmentOnAttachListener =
        FragmentOnAttachListener { _, fragment ->
            when (fragment) {
                is ThumbnailInfoFragment -> fragment.onStateListener = { state ->
                    state.run {
                        eventShowImageViewerFragment.sign(viewModel) {
                            viewModel.showImageViewerFragment(it)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
        super.onCreate(savedInstanceState)
        binding =
            ActivityThumbnailInfoBinding.inflate(layoutInflater).apply { setContentView(root) }

        savedInstanceState ?: with(viewModel.stateFlow.value) {
            supportFragmentManager.beginTransaction().add(
                binding.activityContainer.id,
                ThumbnailInfoFragment.create(argThumbnailVO),
                TAG_THUMBNAIL_INFO
            ).commit()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    with(state) {
                        eventShowImageViewerFragment.sign(viewModel) {
                            ImageViewerFragment.create(it)
                                .show(supportFragmentManager, TAG_IMAGE_VIEWER)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.removeFragmentOnAttachListener(fragmentOnAttachListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }
}