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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
                    fragment.onStateListener = { state ->
                        state.eventGoToThumbnailInfoActivity.sign(viewModel) {
                            viewModel.goToThumbnailInfoActivity(it)
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        savedInstanceState ?: supportFragmentManager.beginTransaction().add(
            binding.activityFrameLayoutContainer.id,
            ThumbnailListFragment.create(),
            TAG_THUMBNAIL_LIST
        ).commit()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    with(state) {
                        eventGoToThumbnailInfoActivity.sign(viewModel) {
                            startActivity(ThumbnailInfoActivity.createIntent(this@MainActivity, it))
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