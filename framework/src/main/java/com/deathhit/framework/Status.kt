package com.deathhit.framework

import androidx.lifecycle.ViewModelStoreOwner

interface Status<Content> {
    fun signForStatus(viewModelStoreOwner: ViewModelStoreOwner): Content?
}