package com.deathhit.lib_state_package

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import java.util.*

class StatePackage<Content>(override val content: Content? = null) : Event<Content>,
    Status<Content> {
    private val signatureMap: WeakHashMap<Any, Boolean> by lazy { WeakHashMap() }

    override fun signForEvent(
        viewModelStoreOwner: ViewModelStoreOwner,
        onSigned: (content: Content) -> Unit
    ) {
        sign(viewModelStoreOwner.viewModelStore)?.let(onSigned)
    }

    override fun signForStatus(any: Any, onSigned: (content: Content) -> Unit) {
        sign(any)?.let(onSigned)
    }

    override fun signForViewStatus(fragment: Fragment, onSigned: (content: Content) -> Unit) {
        sign(fragment.view)?.let(onSigned)
    }

    private fun sign(any: Any?): Content? {
        return if (content == null || signatureMap[any] == true)
            null
        else {
            signatureMap[any] = true
            content
        }
    }
}