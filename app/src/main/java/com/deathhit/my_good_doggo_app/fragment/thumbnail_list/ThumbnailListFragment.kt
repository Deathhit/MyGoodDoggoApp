package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.my_good_doggo_app.databinding.FragmentThumbnailListBinding
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailListFragment : Fragment() {
    companion object {
        fun create() = ThumbnailListFragment()
    }

    var onCallbackListener: ((ThumbnailListViewModel.Callback) -> Unit)? = null

    private val binding get() = _binding!!
    private var _binding: FragmentThumbnailListBinding? = null

    private val viewModel: ThumbnailListViewModel by viewModels()

    private val thumbnailAdapter: ThumbnailAdapter get() = _thumbnailAdapter!!
    private var _thumbnailAdapter: ThumbnailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentThumbnailListBinding.inflate(inflater, container, false).run {
        _binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerView) {
            setHasFixedSize(true)

            val loadStateAdapter = object : LoadStateAdapter() {
                override fun onRetryLoading() {
                    thumbnailAdapter.retry()
                }
            }

            _thumbnailAdapter = object : ThumbnailAdapter() {
                override fun onClickItem(thumbnailVO: ThumbnailVO) {
                    viewModel.onClickItem(thumbnailVO)
                }
            }

            adapter = thumbnailAdapter.withLoadStateFooter(loadStateAdapter)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.callbackFlow.collect {
                        onCallbackListener?.invoke(it)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.thumbnailListFlow.collectLatest {
                thumbnailAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        _thumbnailAdapter = null
    }
}