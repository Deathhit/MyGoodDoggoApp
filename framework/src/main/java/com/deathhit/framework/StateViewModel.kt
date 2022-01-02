package com.deathhit.framework

import android.app.Application
import androidx.lifecycle.*

abstract class StateViewModel<State>(application: Application) : AndroidViewModel(application) {
    private val state: State by lazy { createState() }

    private val stateLiveData: MutableLiveData<State> by lazyOf(MutableLiveData<State>())

    private var isDataInvalid: Boolean = true

    fun getStateLiveData(): LiveData<State> {
        return stateLiveData
    }

    fun loadData(isReload: Boolean = true) {
        if (isDataInvalid || isReload) {
            isDataInvalid = false
            onLoadData()
        }
    }

    protected open fun onLoadData() {

    }

    protected fun postState() {
        stateLiveData.value = state
    }

    protected abstract fun createState(): State
}