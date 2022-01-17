package com.deathhit.framework.toolbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.deathhit.framework.StateViewModel

interface StateComponent<State, ViewModel : StateViewModel<State>> : LifecycleOwner {
    val viewModel: ViewModel
    fun createViewModel(savedInstanceState: Bundle?): ViewModel
    fun onRenderState(state: State)

    fun createViewModelInternal(savedInstanceState: Bundle?): ViewModel {
        val viewModel = createViewModel(savedInstanceState)
        viewModel.loadData(false)
        return viewModel
    }

    fun onFragmentAttach(fragment: Fragment) {

    }
}