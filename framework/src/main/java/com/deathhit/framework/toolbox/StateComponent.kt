package com.deathhit.framework.toolbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.deathhit.framework.StateViewModel

interface StateComponent<State, ViewModel : StateViewModel<State>> {
    fun createViewModel(args: Bundle): ViewModel
    fun onFragmentAttach(fragment: Fragment)
    fun onSaveViewModelArgs(args: Bundle)
}