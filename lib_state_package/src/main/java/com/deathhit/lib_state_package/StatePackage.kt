package com.deathhit.lib_state_package

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelStoreOwner
import java.util.*

class StatePackage<Content>(override val content: Content?) : Event<Content>, Status<Content> {
    private val signatureMap: WeakHashMap<Any, Boolean> = WeakHashMap()

    constructor() : this(null)

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
        return if (isSigned(any))
            null
        else {
            signatureMap[any] = true
            content
        }
    }

    private fun isSigned(any: Any?): Boolean {
        signatureMap[any]?.let { return it } ?: run { return false }
    }
}