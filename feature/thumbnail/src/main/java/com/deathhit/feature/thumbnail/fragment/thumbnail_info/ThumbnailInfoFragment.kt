package com.deathhit.feature.thumbnail.fragment.thumbnail_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import com.deathhit.feature.thumbnail.databinding.FragmentThumbnailInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailInfoFragment : Fragment() {
    companion object {
        fun create(thumbnailId: String) = ThumbnailInfoFragment().apply {
            arguments = ThumbnailInfoViewModel.createArgs(thumbnailId)
        }
    }

    interface Callback {
        fun onOpenImage(imageUrl: String)
    }

    var callback: Callback? = null

    private val binding get() = _binding!!
    private var _binding: FragmentThumbnailInfoBinding? = null

    private val viewModel: ThumbnailInfoViewModel by viewModels()

    private val bannerAdapter: BannerAdapter get() = _bannerAdapter!!
    private var _bannerAdapter: BannerAdapter? = null

    private val breedAdapter: BreedAdapter get() = _breedAdapter!!
    private var _breedAdapter: BreedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentThumbnailInfoBinding.inflate(inflater, container, false).run {
        _binding = this
        root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerView) {
            setHasFixedSize(true)

            _bannerAdapter = object : BannerAdapter() {
                override fun onBannerClick(imageUrl: String) {
                    viewModel.openImage(imageUrl)
                }
            }

            _breedAdapter = BreedAdapter()

            adapter = ConcatAdapter(bannerAdapter, breedAdapter)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.stateFlow.map { it.actions }.distinctUntilChanged().collect {
                        it.forEach { action ->
                            when(action) {
                                is ThumbnailInfoViewModel.State.Action.OpenImage -> callback?.onOpenImage(action.imageUrl)
                            }

                            viewModel.onAction(action)
                        }
                    }
                }

                launch {
                    viewModel.stateFlow.map { it.breedList }.distinctUntilChanged().collect {
                        breedAdapter.submitList(it)
                    }
                }

                launch {
                    viewModel.stateFlow.map { it.thumbnail }.distinctUntilChanged().collect {
                        bannerAdapter.notifyBannerUrlChanged(it?.thumbnailUrl)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        _bannerAdapter = null
        _breedAdapter = null
    }
}