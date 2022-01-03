package com.deathhit.my_good_doggo_app.thumbnail_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.framework.toolbox.StateFragment

class ThumbnailInfoFragment :
    StateFragment<ThumbnailInfoViewModel.State, ThumbnailInfoViewModel>() {
    companion object {
        private const val TAG = "ThumbnailInfoFragment"
        private const val KEY_THUMBNAIL_VO = "$TAG.KEY_THUMBNAIL_VO"
        private const val ID_RECYCLER_VIEW = R.id.recyclerView
        private const val LAYOUT = R.layout.fragment_thumbnail_info

        fun create(thumbnailVO: ThumbnailVO): ThumbnailInfoFragment {
            val args = Bundle()
            args.putParcelable(KEY_THUMBNAIL_VO, thumbnailVO)
            val fragment = ThumbnailInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var recyclerView: RecyclerView? = null

    private var breedAdapter: BreedAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(LAYOUT, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(ID_RECYCLER_VIEW).apply {
            setHasFixedSize(true)
            breedAdapter = createBreedAdapter().also { adapter = createConcatAdapter(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null

        breedAdapter = null
    }

    override fun createViewModel(savedInstanceState: Bundle?): ThumbnailInfoViewModel {
        val args = savedInstanceState ?: arguments ?: Bundle()
        val viewModel: ThumbnailInfoViewModel by viewModels()
        viewModel.thumbnailVO = args.getParcelable(KEY_THUMBNAIL_VO)
        return viewModel
    }

    override fun onRenderState(state: ThumbnailInfoViewModel.State) {
        state.statusBreedList.signForStatus(this)
            ?.let { breedAdapter?.submitList(ArrayList(it)) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_THUMBNAIL_VO, viewModel.thumbnailVO)
        super.onSaveInstanceState(outState)
    }

    private fun createBannerAdapter() =
        object : RecyclerView.Adapter<BannerViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder =
                BannerViewHolder(parent)

            override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
                viewModel.thumbnailVO?.let { item ->
                    Glide.with(holder.imageBanner).load(item.thumbnailUrl)
                        .fitCenter().format(DecodeFormat.PREFER_RGB_565).into(holder.imageBanner)
                }
            }

            override fun getItemCount(): Int = 1
        }

    private fun createBreedAdapter() = BreedAdapter()

    private fun createConcatAdapter(breedAdapter: BreedAdapter): RecyclerView.Adapter<*> =
        ConcatAdapter(createBannerAdapter(), breedAdapter)
}