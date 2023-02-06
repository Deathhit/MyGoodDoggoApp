package com.deathhit.feature.thumbnail.fragment.thumbnail_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.feature.thumbnail.databinding.FragmentThumbnailListBinding
import com.deathhit.feature.thumbnail.model.Thumbnail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailListFragment : Fragment() {
    companion object {
        fun create() = ThumbnailListFragment()
    }

    interface Callback {
        fun onOpenThumbnail(thumbnailId: String)
    }

    var callback: Callback? = null

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
                override fun onClickItem(thumbnail : Thumbnail) {
                    viewModel.openThumbnail(thumbnail)
                }
            }

            adapter = thumbnailAdapter.withLoadStateFooter(loadStateAdapter)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.stateFlow.map { it.actions }.distinctUntilChanged().collect {
                        it.forEach { action ->
                            when(action) {
                                is ThumbnailListViewModel.State.Action.OpenThumbnail -> callback?.onOpenThumbnail(action.thumbnailId)
                            }

                            viewModel.onAction(action)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.thumbnailPagingDataFlow.collectLatest {
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