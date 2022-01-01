package com.deathhit.framework.toolbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.deathhit.framework.StateViewModel

interface StateComponent<State, ViewModel : StateViewModel<State>> : LifecycleOwner {
    val viewModel: ViewModel
    fun createViewModel(args: Bundle): ViewModel
    fun onFragmentAttach(fragment: Fragment)
    fun onRenderState(state: State)
    fun onSaveViewModelArgs(args: Bundle)

    fun <State, ViewModel : StateViewModel<State>> observeState(
        stateComponent: StateComponent<State, ViewModel>,
        observer: Observer<State>
    ) {
        stateComponent.viewModel.getStateLiveData().observe(this, observer)
    }
}