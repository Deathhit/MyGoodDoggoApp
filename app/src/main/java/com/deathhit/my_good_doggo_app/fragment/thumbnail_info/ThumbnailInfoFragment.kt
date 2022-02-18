package com.deathhit.my_good_doggo_app.fragment.thumbnail_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.databinding.FragmentThumbnailInfoBinding
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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

    private val binding: FragmentThumbnailInfoBinding get() = _binding!!

    private val viewModel: ThumbnailInfoViewModel by viewModels()

    private var _binding: FragmentThumbnailInfoBinding? = null

    private var bannerAdapter: BannerAdapter? = null
    private var breedAdapter: BreedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                state.statusBreedVOList.signForViewStatus(this@ThumbnailInfoFragment) {
                    breedAdapter?.submitList(ArrayList(it))
                }

                state.statusThumbnailVOUpdated.signForViewStatus(this@ThumbnailInfoFragment) {
                    bannerAdapter?.notifyOnItemChanged(state.attrThumbnailVO)
                }
            }
        }
    }

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
        binding.recyclerView.apply {
            setHasFixedSize(true)
            bannerAdapter = BannerAdapter()
            breedAdapter = BreedAdapter()
            adapter = createConcatAdapter(bannerAdapter!!, breedAdapter!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        bannerAdapter = null
        breedAdapter = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    private fun createConcatAdapter(bannerAdapter: BannerAdapter, breedAdapter: BreedAdapter): RecyclerView.Adapter<*> =
        ConcatAdapter(bannerAdapter, breedAdapter)
}