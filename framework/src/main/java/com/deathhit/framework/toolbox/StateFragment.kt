package com.deathhit.framework.toolbox

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.deathhit.framework.StateViewModel

abstract class StateFragment<State, ViewModel : StateViewModel<State>> : Fragment(),
    StateComponent<State, ViewModel> {

    private val fragmentOnAttachListener: FragmentOnAttachListener =
        FragmentOnAttachListener { _, fragment ->
            onFragmentAttach(
                fragment
            )
        }

    private var stateLiveData: MutableLiveData<State>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        stateLiveData = MutableLiveData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        observeFragmentAttachment()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getStateLiveData().observe(viewLifecycleOwner, {
            onRenderState(it)
            stateLiveData!!.value = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        stopObservingFragmentAttachment()
    }

    override fun onDetach() {
        stateLiveData = null
        super.onDetach()
    }

    fun observeState(lifecycleOwner: LifecycleOwner, observer: Observer<State>) {
        stateLiveData?.observe(lifecycleOwner, observer) ?: throw IllegalStateException("Can't observe state before onAttach()!")
    }

    private fun observeFragmentAttachment() {
        childFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
    }

    private fun stopObservingFragmentAttachment() {
        childFragmentManager.removeFragmentOnAttachListener(fragmentOnAttachListener)
    }
}