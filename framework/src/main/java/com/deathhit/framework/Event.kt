package com.deathhit.framework

import androidx.lifecycle.ViewModelStoreOwner

interface Event<Content> {
    fun signForEvent(viewModelStoreOwner: ViewModelStoreOwner): Content?
}