package com.deathhit.my_good_doggo_app.thumbnail_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.R
import com.deathhit.my_good_doggo_app.base.model.ThumbnailVO
import kotlinx.coroutines.flow.*

class ThumbnailListFragment : Fragment() {
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

    private val viewModel: ThumbnailListViewModel by viewModels()

    private var recyclerView: RecyclerView? = null

    private var thumbnailAdapter: ThumbnailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                state.statusThumbnailList.signForStatus(this@ThumbnailListFragment) {
                    thumbnailAdapter?.submitData(lifecycle, it)
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
            thumbnailAdapter = createThumbnailAdapter().also { adapter = createConcatAdapter(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView = null

        thumbnailAdapter = null
    }

    fun getStateFlow() = viewModel.stateFlow

    private fun createConcatAdapter(thumbnailAdapter: ThumbnailAdapter): RecyclerView.Adapter<*> =
        thumbnailAdapter.withLoadStateFooter(createLoadStateAdapter())

    private fun createLoadStateAdapter(): LoadStateAdapter = object : LoadStateAdapter() {
        override fun onRetryLoading() {
            thumbnailAdapter?.retry()
        }
    }

    private fun createThumbnailAdapter(): ThumbnailAdapter = object : ThumbnailAdapter() {
        override fun onClickItem(thumbnailVO: ThumbnailVO) {
            viewModel.goToThumbnailInfoActivity(thumbnailVO)
        }
    }
}