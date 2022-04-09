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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThumbnailInfoFragment : Fragment() {
    companion object {
        fun create(thumbnailVO: ThumbnailVO): ThumbnailInfoFragment {
            val args = Bundle()
            args.putParcelable(ThumbnailInfoViewModel.KEY_THUMBNAIL_VO, thumbnailVO)
            val fragment = ThumbnailInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    var onStateListener: ((ThumbnailInfoViewModel.State) -> Unit)? = null

    private val binding: FragmentThumbnailInfoBinding get() = _binding!!
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
    ): View {
        _binding = FragmentThumbnailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.recyclerView) {
            setHasFixedSize(true)

            _bannerAdapter = object : BannerAdapter() {
                override fun onBannerClick(item: ThumbnailVO?) {
                    viewModel.viewImage(item)
                }
            }

            _breedAdapter = BreedAdapter()

            adapter = ConcatAdapter(bannerAdapter, breedAdapter)
        }

        with(viewModel.stateFlow.value) {
            bannerAdapter.notifyOnItemChanged(argThumbnailVO)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    with(state) {
                        statusBreedVOList.sign(binding) {
                            breedAdapter.submitList(it)
                        }

                        onStateListener?.invoke(this)
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

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }
}