package com.deathhit.framework

import androidx.lifecycle.ViewModelStoreOwner

interface Event<Content> {
    val content: Content?
    fun signForEvent(viewModelStoreOwner: ViewModelStoreOwner, onSigned: (content: Content) -> Unit)
}