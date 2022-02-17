package com.deathhit.lib_state_package

import androidx.lifecycle.ViewModelStoreOwner

interface Event<Content> {
    val content: Content?
    fun signForEvent(viewModelStoreOwner: ViewModelStoreOwner, onSigned: (content: Content) -> Unit)
}