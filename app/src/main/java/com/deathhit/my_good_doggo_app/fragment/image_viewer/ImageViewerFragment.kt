package com.deathhit.my_good_doggo_app.fragment.image_viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.deathhit.my_good_doggo_app.databinding.FragmentImageViewerBinding
import kotlinx.coroutines.flow.collect

class ImageViewerFragment : Fragment() {
    companion object {
        fun create(imageUrl: String): ImageViewerFragment {
            val args = Bundle()
            args.putString(ImageViewerViewModel.KEY_IMAGE_URL, imageUrl)
            val fragment = ImageViewerFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val binding get() = _binding!!

    private val viewModel: ImageViewerViewModel by viewModels()

    private var _binding: FragmentImageViewerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                state.statusImageUrl.signForViewStatus(this@ImageViewerFragment) {
                    val imageView = binding.imageView
                    Glide.with(imageView).load(it).into(imageView)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageViewerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }
}