package com.deathhit.my_good_doggo_app.fragment.thumbnail_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deathhit.my_good_doggo_app.databinding.FragmentThumbnailListBinding
import com.deathhit.my_good_doggo_app.model.ThumbnailVO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    var onStateListener: ((ThumbnailListViewModel.State) -> Unit)? = null

    private val binding: FragmentThumbnailListBinding get() = _binding!!
    private var _binding: FragmentThumbnailListBinding? = null

    private val viewModel: ThumbnailListViewModel by viewModels()

    private var thumbnailAdapter: ThumbnailAdapter? = null

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
        with(binding.recyclerView) {
            setHasFixedSize(true)

            val loadStateAdapter = object : LoadStateAdapter() {
                override fun onRetryLoading() {
                    thumbnailAdapter?.retry()
                }
            }

            thumbnailAdapter = object : ThumbnailAdapter() {
                override fun onClickItem(thumbnailVO: ThumbnailVO) {
                    viewModel.goToThumbnailInfoActivity(thumbnailVO)
                }
            }

            adapter = thumbnailAdapter!!.withLoadStateFooter(loadStateAdapter)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { state ->
                    with(state) {
                        statusThumbnailList.sign(binding) {
                            thumbnailAdapter?.submitData(lifecycle, it)
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

        thumbnailAdapter = null
    }
}