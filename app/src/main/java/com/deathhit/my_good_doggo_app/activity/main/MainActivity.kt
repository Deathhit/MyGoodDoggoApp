package com.deathhit.my_good_doggo_app.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.my_good_doggo_app.activity.thumbnail_info.ThumbnailInfoActivity
import com.deathhit.my_good_doggo_app.databinding.ActivityMainBinding
import com.deathhit.my_good_doggo_app.fragment.thumbnail_list.ThumbnailListFragment
import com.deathhit.my_good_doggo_app.fragment.thumbnail_list.ThumbnailListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val TAG_THUMBNAIL_LIST = "$TAG.TAG_THUMBNAIL_LIST"
    }

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    private val fragmentOnAttachListener: FragmentOnAttachListener =
        FragmentOnAttachListener { _, fragment ->
            when (fragment) {
                is ThumbnailListFragment -> {
                    fragment.onCallbackListener = { callback ->
                        when (callback) {
                            is ThumbnailListViewModel.Callback.OnClickItem -> viewModel.onClickThumbnail(
                                callback.thumbnail
                            )
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
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
                    viewModel.eventFlow.collect { event ->
                        when (event) {
                            is MainActivityViewModel.Event.GoToThumbnailInfo -> startActivity(
                                ThumbnailInfoActivity.createIntent(
                                    this@MainActivity,
                                    event.thumbnail
                                )
                            )
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
}