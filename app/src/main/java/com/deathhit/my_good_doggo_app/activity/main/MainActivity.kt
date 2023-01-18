package com.deathhit.my_good_doggo_app.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.feature.thumbnail.thumbnail_list.ThumbnailListFragment
import com.deathhit.my_good_doggo_app.activity.thumbnail_info.ThumbnailInfoActivity
import com.deathhit.my_good_doggo_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val TAG_THUMBNAIL_LIST = "$TAG.TAG_THUMBNAIL_LIST"
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

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

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        savedInstanceState ?: supportFragmentManager.beginTransaction().add(
            binding.activityContainer.id,
            ThumbnailListFragment.create(),
            TAG_THUMBNAIL_LIST
        ).commit()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.stateFlow.map { it.actions }.distinctUntilChanged().collect {
                        it.forEach { action ->
                            when (action) {
                                is MainActivityViewModel.State.Action.GoToThumbnailInfo -> startActivity(
                                    ThumbnailInfoActivity.createIntent(
                                        this@MainActivity,
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