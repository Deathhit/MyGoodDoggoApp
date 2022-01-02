package com.deathhit.framework.toolbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentOnAttachListener
import com.deathhit.framework.StateViewModel

abstract class StateActivity<State, ViewModel : StateViewModel<State>> : AppCompatActivity(),
    StateComponent<State, ViewModel> {
    override val viewModel: ViewModel by lazy { createViewModelInternal(savedInstanceState) }

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
        viewModel.getStateLiveData().observe(this, { onRenderState(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        stopObservingFragmentAttachment()
    }

    private fun observeFragmentAttachment() {
        supportFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
    }

    private fun stopObservingFragmentAttachment() {
        supportFragmentManager.removeFragmentOnAttachListener(fragmentOnAttachListener)
    }
}