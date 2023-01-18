package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.feature.image_viewer.ImageViewerFragment
import com.deathhit.feature.thumbnail.thumbnail_info.ThumbnailInfoFragment
import com.deathhit.my_good_doggo_app.databinding.ActivityThumbnailInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailInfoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ThumbnailInfoActivity"
        private const val TAG_IMAGE_VIEWER = "$TAG.TAG_IMAGE_VIEWER"
        private const val TAG_THUMBNAIL_INFO = "$TAG.TAG_THUMBNAIL_INFO"

        fun createIntent(context: Context, thumbnailId: String) =
            Intent(context, ThumbnailInfoActivity::class.java).apply {
                putExtras(ThumbnailInfoActivityViewModel.createArgs(thumbnailId))
            }
    }

    private lateinit var binding: ActivityThumbnailInfoBinding

    private val viewModel: ThumbnailInfoActivityViewModel by viewModels()
    private val thumbnailId get() = viewModel.stateFlow.value.thumbnailId

    private val imageViewerFragment
        get() = supportFragmentManager.findFragmentByTag(
            TAG_IMAGE_VIEWER
        ) as ImageViewerFragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            when (fragment) {
                is ThumbnailInfoFragment -> fragment.callback =
                    object : ThumbnailInfoFragment.Callback {
                        override fun onOpenImage(imageUrl: String) {
                            viewModel.openImage(imageUrl)
                        }
                    }
            }
        }

        super.onCreate(savedInstanceState)

        binding =
            ActivityThumbnailInfoBinding.inflate(layoutInflater).also { setContentView(it.root) }

        savedInstanceState ?: run {
            supportFragmentManager.beginTransaction().add(
                binding.activityContainer.id,
                ThumbnailInfoFragment.create(thumbnailId),
                TAG_THUMBNAIL_INFO
            ).commit()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.stateFlow.map { it.actions }.distinctUntilChanged().collect {
                        it.forEach { action ->
                            when (action) {
                                is ThumbnailInfoActivityViewModel.State.Action.OpenImage -> {
                                    imageViewerFragment?.dismiss()
                                    ImageViewerFragment.create(
                                        action.imageUrl
                                    ).show(supportFragmentManager, TAG_IMAGE_VIEWER)
                                }
                            }

                            viewModel.onAction(action)
                        }
                    }
                }
            }
        }
    }
}