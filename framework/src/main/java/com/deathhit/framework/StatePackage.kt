package com.deathhit.framework

import androidx.lifecycle.ViewModelStoreOwner
import com.deathhit.environment.ObjectPackage

class StatePackage<Content> : ObjectPackage<Content, Int>(), Event<Content>, Status<Content> {
    override fun signForEvent(viewModelStoreOwner: ViewModelStoreOwner): Content? {
        return sign(viewModelStoreOwner.viewModelStore.hashCode())
    }

    override fun signForStatus(viewModelStoreOwner: ViewModelStoreOwner): Content? {
        return sign(viewModelStoreOwner.hashCode())
    }
}