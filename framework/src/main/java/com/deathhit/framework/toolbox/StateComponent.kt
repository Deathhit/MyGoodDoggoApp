package com.deathhit.framework.toolbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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

    fun <State, ViewModel : StateViewModel<State>> observeState(
        stateComponent: StateComponent<State, ViewModel>,
        observer: Observer<State>
    ) {
        stateComponent.viewModel.getStateLiveData().observe(this, observer)
    }

    fun onFragmentAttach(fragment: Fragment) {

    }
}