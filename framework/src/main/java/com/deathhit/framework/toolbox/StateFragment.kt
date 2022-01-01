package com.deathhit.framework.toolbox

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import com.deathhit.framework.StateViewModel

abstract class StateFragment<State, ViewModel : StateViewModel<State>> : Fragment(),
    StateComponent<State, ViewModel> {
    override val viewModel by lazy { createViewModelInternal() }

    private val fragmentOnAttachListener: FragmentOnAttachListener =
        FragmentOnAttachListener { _, fragment ->
            onFragmentAttach(
                fragment
            )
        }

    private var savedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        observeFragmentAttachment()
        super.onCreate(savedInstanceState)
        this.savedInstanceState = savedInstanceState
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStateLiveData().observe(viewLifecycleOwner, { onRenderState(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        stopObservingFragmentAttachment()
    }

    override fun onFragmentAttach(fragment: Fragment) {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        onSaveViewModelArgs(outState)
        super.onSaveInstanceState(outState)
    }

    private fun createViewModelInternal(): ViewModel = createViewModel(getViewModelArgs())

    private fun getViewModelArgs(): Bundle = savedInstanceState ?: arguments ?: Bundle()

    private fun observeFragmentAttachment() {
        childFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
    }

    private fun stopObservingFragmentAttachment() {
        childFragmentManager.removeFragmentOnAttachListener(fragmentOnAttachListener)
    }
}