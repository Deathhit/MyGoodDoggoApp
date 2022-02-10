package com.deathhit.my_good_doggo_app.thumbnail_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import kotlinx.coroutines.flow.collect

class ThumbnailInfoFragment : Fragment() {
    companion object {
        private const val ID_RECYCLER_VIEW = R.id.recyclerView
        private const val LAYOUT = R.layout.fragment_thumbnail_info

        fun create(thumbnailVO: ThumbnailVO): ThumbnailInfoFragment {
            val args = Bundle()
            args.putParcelable(ThumbnailInfoViewModel.KEY_THUMBNAIL_VO, thumbnailVO)
            val fragment = ThumbnailInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModel: ThumbnailInfoViewModel by viewModels()

    private var recyclerView: RecyclerView? = null

    private var bannerAdapter: BannerAdapter? = null
    private var breedAdapter: BreedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                state.statusBreedVOList.signForStatus(this@ThumbnailInfoFragment) {
                    breedAdapter?.submitList(ArrayList(it))
                }

                state.statusThumbnailVO.signForStatus(this@ThumbnailInfoFragment) {
                    bannerAdapter?.notifyOnItemChanged(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(LAYOUT, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(ID_RECYCLER_VIEW).apply {
            setHasFixedSize(true)
            bannerAdapter = createBannerAdapter()
            breedAdapter = createBreedAdapter()
            adapter = createConcatAdapter(bannerAdapter!!, breedAdapter!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null

        bannerAdapter = null
        breedAdapter = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    private fun createBannerAdapter() = BannerAdapter()

    private fun createBreedAdapter() = BreedAdapter()

    private fun createConcatAdapter(bannerAdapter: BannerAdapter, breedAdapter: BreedAdapter): RecyclerView.Adapter<*> =
        ConcatAdapter(bannerAdapter, breedAdapter)
}