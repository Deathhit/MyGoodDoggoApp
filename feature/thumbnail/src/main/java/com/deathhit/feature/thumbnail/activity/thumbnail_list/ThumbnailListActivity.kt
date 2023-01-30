package com.deathhit.feature.thumbnail.activity.thumbnail_list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.feature.thumbnail.activity.thumbnail_info.ThumbnailInfoActivity
import com.deathhit.feature.thumbnail.databinding.ActivityThumbnailListBinding
import com.deathhit.feature.thumbnail.fragment.thumbnail_list.ThumbnailListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailListActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val TAG_THUMBNAIL_LIST = "$TAG.TAG_THUMBNAIL_LIST"
    }

    private lateinit var binding: ActivityThumbnailListBinding

    private val viewModel: ThumbnailListActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            when (fragment) {
                is ThumbnailListFragment -> fragment.callback =
                    object : ThumbnailListFragment.Callback {
                        override fun onOpenThumbnail(thumbnailId: String) {
                            viewModel.goToThumbnailInfo(thumbnailId)
                        }
                    }
            }
        }

        super.onCreate(savedInstanceState)

        binding = ActivityThumbnailListBinding.inflate(layoutInflater).also { setContentView(it.root) }

        savedInstanceState ?: supportFragmentManager.commit {
            add(
                binding.activityContainer.id,
                ThumbnailListFragment.create(),
                TAG_THUMBNAIL_LIST
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.stateFlow.map { it.actions }.distinctUntilChanged().collect {
                        it.forEach { action ->
                            when (action) {
                                is ThumbnailListActivityViewModel.State.Action.GoToThumbnailInfo -> startActivity(
                                    ThumbnailInfoActivity.createIntent(
                                        this@ThumbnailListActivity,
                                        action.thumbnailId
                                    )
                                )
                            }

                            viewModel.onAction(action)
                        }
                    }
                }
            }
        }
    }
}