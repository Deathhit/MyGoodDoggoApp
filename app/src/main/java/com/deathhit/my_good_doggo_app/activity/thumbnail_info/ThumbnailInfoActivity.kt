package com.deathhit.my_good_doggo_app.activity.thumbnail_info

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.databinding.ActivityThumbnailInfoBinding
import com.deathhit.my_good_doggo_app.fragment.image_viewer.ImageViewerFragment
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import com.deathhit.my_good_doggo_app.fragment.thumbnail_info.ThumbnailInfoFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailInfoActivity : AppCompatActivity() {
    companion object {
        private const val TAG =
            "com.deathhit.my_good_doggo_app.activity.thumbnail_info.ThumbnailInfoActivity"
        private const val TAG_IMAGE_VIEWER = "$TAG.TAG_IMAGE_VIEWER"
        private const val TAG_THUMBNAIL_INFO = "$TAG.TAG_THUMBNAIL_INFO"

        private const val ID_CONTAINER = R.id.activity_frameLayout_container

        fun createIntent(context: Context, thumbnailVO: ThumbnailVO): Intent {
            val intent = Intent(context, ThumbnailInfoActivity::class.java)
            intent.putExtra(ThumbnailInfoActivityViewModel.KEY_THUMBNAIL_VO, thumbnailVO)
            return intent
        }
    }

    private val fragmentOnAttachListener: FragmentOnAttachListener =
        FragmentOnAttachListener { _, fragment ->
            onFragmentAttach(
                fragment
            )
        }

    private val viewModel: ThumbnailInfoActivityViewModel by viewModels()

    private lateinit var binding: ActivityThumbnailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
        super.onCreate(savedInstanceState)
        binding = ActivityThumbnailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState ?: viewModel.addThumbnailInfoFragment()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    state.eventAddThumbnailInfoFragment.sign(viewModel) {
                        addThumbnailListFragment(it)
                    }

                    state.eventShowImageViewerFragment.sign(viewModel) {
                        showImageViewerFragment(it)
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

    private fun onFragmentAttach(fragment: Fragment) {
        if (fragment is ThumbnailInfoFragment)
            fragment.setStateListener { state ->
                state.eventShowImageViewerFragment.sign(viewModel) {
                    viewModel.showImageViewerFragment(it)
                }
            }
    }

    private fun addThumbnailListFragment(thumbnailVO: ThumbnailVO) {
        supportFragmentManager.beginTransaction().add(
            ID_CONTAINER,
            ThumbnailInfoFragment.create(thumbnailVO),
            TAG_THUMBNAIL_INFO
        ).commit()
    }

    private fun showImageViewerFragment(imageUrl: String) {
        supportFragmentManager.beginTransaction()
            .replace(ID_CONTAINER, ImageViewerFragment.create(imageUrl), TAG_IMAGE_VIEWER)
            .addToBackStack(null).commit()
    }
}