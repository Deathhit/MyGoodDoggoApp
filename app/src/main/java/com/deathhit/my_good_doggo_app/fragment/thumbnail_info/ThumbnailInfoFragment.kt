package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

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
import com.deathhit.my_good_doggo_app.databinding.FragmentThumbnailInfoBinding
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailInfoFragment : Fragment() {
    companion object {
        fun create(thumbnail: ThumbnailVO) = ThumbnailInfoFragment().apply {
            arguments = ThumbnailInfoViewModel.createArgs(thumbnail)
        }
    }

    var onCallbackListener: ((ThumbnailInfoViewModel.Callback) -> Unit)? = null

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
                override fun onBannerClick(item: ThumbnailVO) {
                    viewModel.onBannerClick(item)
                }
            }

            _breedAdapter = BreedAdapter()

            adapter = ConcatAdapter(bannerAdapter, breedAdapter)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.callbackFlow.collect {
                        onCallbackListener?.invoke(it)
                    }
                }

                launch {
                    viewModel.stateFlow.map { it.breedList }.distinctUntilChanged().collect {
                        breedAdapter.submitList(it)
                    }
                }

                launch {
                    viewModel.stateFlow.map { it.thumbnail }.distinctUntilChanged().collect {
                        bannerAdapter.notifyOnItemChanged(it)
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