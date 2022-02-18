package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.deathhit.my_good_doggo_app.databinding.FragmentThumbnailListBinding
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*

@AndroidEntryPoint
class ThumbnailListFragment : Fragment() {
    companion object {
        fun create(): ThumbnailListFragment {
            val args = Bundle()
            val fragment = ThumbnailListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val binding: FragmentThumbnailListBinding get() = _binding!!
    private var _binding: FragmentThumbnailListBinding? = null

    private val viewModel: ThumbnailListViewModel by viewModels()

    private var thumbnailAdapter: ThumbnailAdapter? = null

    private var onStateListener: ((ThumbnailListViewModel.State) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThumbnailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            thumbnailAdapter = createThumbnailAdapter().also { adapter = createConcatAdapter(it) }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { state ->
                state.statusThumbnailList.signForViewStatus(this@ThumbnailListFragment) {
                    thumbnailAdapter?.submitData(lifecycle, it)
                }

                onStateListener?.invoke(state)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        thumbnailAdapter = null
    }

    fun setStateListener(onStateListener: ((ThumbnailListViewModel.State) -> Unit)?) {
        this.onStateListener = onStateListener
    }

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