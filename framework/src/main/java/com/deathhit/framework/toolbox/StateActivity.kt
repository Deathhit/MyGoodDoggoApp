package com.deathhit.framework.toolbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentOnAttachListener
import com.deathhit.framework.StateViewModel

abstract class StateActivity<State, ViewModel : StateViewModel<State>> : AppCompatActivity(),
    StateComponent<State, ViewModel> {
    val viewModel: ViewModel by lazy { createViewModelInternal() }

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

    override fun onDestroy() {
        super.onDestroy()
        stopObservingFragmentAttachment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        onSaveViewModelArgs(outState)
        super.onSaveInstanceState(outState)
    }

    private fun createViewModelInternal(): ViewModel {
        return createViewModel(getViewModelArgs())
    }

    private fun getViewModelArgs(): Bundle {
        var args = savedInstanceState
        if (args == null)
            args = intent.extras
        if (args == null)
            args = Bundle()
        return args
    }

    private fun observeFragmentAttachment() {
        supportFragmentManager.addFragmentOnAttachListener(fragmentOnAttachListener)
    }

    private fun stopObservingFragmentAttachment() {
        supportFragmentManager.removeFragmentOnAttachListener(fragmentOnAttachListener)
    }
}