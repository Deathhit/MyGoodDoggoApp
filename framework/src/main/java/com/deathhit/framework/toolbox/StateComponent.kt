package com.deathhit.framework.toolbox

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.deathhit.framework.StateViewModel

interface StateComponent<State, ViewModel : StateViewModel<State>> : LifecycleOwner {
    val viewModel: ViewModel
    fun onRenderState(state: State)

    fun onFragmentAttach(fragment: Fragment) {

    }
}