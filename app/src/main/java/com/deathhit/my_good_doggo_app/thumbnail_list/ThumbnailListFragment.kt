package com.deathhit.my_good_doggo_app.thumbnail_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import com.deathhit.framework.toolbox.StateFragment

class ThumbnailListFragment :
    StateFragment<ThumbnailListViewModel.State, ThumbnailListViewModel>() {
    companion object {
        private const val ID_RECYCLER_VIEW = R.id.recyclerView
        private const val LAYOUT = R.layout.fragment_thumbnail_list

        fun create(): ThumbnailListFragment {
            val args = Bundle()
            val fragment = ThumbnailListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var recyclerView: RecyclerView? = null

    private var thumbnailAdapter: ThumbnailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadThumbnailList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(LAYOUT, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(ID_RECYCLER_VIEW)

        recyclerView?.setHasFixedSize(true)

        thumbnailAdapter = createThumbnailAdapter()
        recyclerView?.adapter = createConcatAdapter()

        viewModel.getStateLiveData().observe(viewLifecycleOwner, { state ->
            state.statusThumbnailList.signForStatus(this)
                ?.let { thumbnailAdapter?.submitData(lifecycle, it) }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null

        thumbnailAdapter = null
    }

    override fun createViewModel(args: Bundle): ThumbnailListViewModel {
        val viewModel: ThumbnailListViewModel by viewModels()
        return viewModel
    }

    override fun onSaveViewModelArgs(args: Bundle) {

    }

    private fun createConcatAdapter(): RecyclerView.Adapter<*> =
        thumbnailAdapter!!.withLoadStateFooter(createLoadStateAdapter())

    private fun createLoadStateAdapter(): LoadStateAdapter = object : LoadStateAdapter() {
        override fun onRetryLoading() {
            thumbnailAdapter!!.retry()
        }
    }

    private fun createThumbnailAdapter(): ThumbnailAdapter = object : ThumbnailAdapter() {
        override fun onClickItem(thumbnailVO: ThumbnailVO) {
            viewModel.goToThumbnailInfoActivity(thumbnailVO)
        }
    }
}