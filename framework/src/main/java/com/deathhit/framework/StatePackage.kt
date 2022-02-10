package com.deathhit.framework

import android.app.Activity
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
        sign(viewModelStoreOwner)?.let(onSigned)
    }

    override fun signForStatus(activity: Activity, onSigned: (content: Content) -> Unit) {
        sign(activity)?.let(onSigned)
    }

    override fun signForStatus(fragment: Fragment, onSigned: (content: Content) -> Unit) {
        sign(fragment)?.let(onSigned)
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