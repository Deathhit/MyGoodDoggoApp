package com.deathhit.framework

import android.app.Application
import androidx.lifecycle.*

abstract class StateViewModel<State>(application: Application) : AndroidViewModel(application) {
    private val state: State by lazy { createState() }

    private val stateLiveData: MutableLiveData<State> by lazyOf(MutableLiveData<State>())

    fun getStateLiveData(): LiveData<State> {
        return stateLiveData
    }

    protected fun postState() {
        stateLiveData.value = state
    }

    protected abstract fun createState(): State
}